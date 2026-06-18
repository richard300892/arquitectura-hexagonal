package com.alediesme.joyeria.shared.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;

@Configuration
@Profile("prod")
public class ProdDataSourceConfig {

  private static final Logger log = LoggerFactory.getLogger(ProdDataSourceConfig.class);

  @Bean
  public DataSource dataSource(@Value("${spring.datasource.jndi-name}") String jndiName)
      throws Exception {

    if (!StringUtils.hasText(jndiName)) {
      throw new IllegalStateException(
          "spring.datasource.jndi-name is required in prod. Set DB_JNDI_NAME (e.g. jdbc/AlediesmeDS).");
    }

    JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
    factoryBean.setJndiName(jndiName);
    factoryBean.setProxyInterface(DataSource.class);
    factoryBean.setLookupOnStartup(false);
    factoryBean.setCache(true);
    factoryBean.afterPropertiesSet();

    log.info("Creating prod DataSource via JNDI resource ref: java:comp/env/{}", jndiName);
    return (DataSource) factoryBean.getObject();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void verifyConnection(ApplicationReadyEvent event) {
    DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);

    try (Connection connection = dataSource.getConnection()) {
      log.info(
          "Database connection verified via JNDI. url={} user={}",
          connection.getMetaData().getURL(),
          connection.getMetaData().getUserName());
    } catch (Exception ex) {
      log.error(
          "Database connection via JNDI failed. Check WebLogic DataSource and DB_JNDI_NAME. Cause: {}",
          ex.getMessage());
    }
  }
}
