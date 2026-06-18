package com.alediesme.joyeria.shared.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@Profile("dev")
@EnableConfigurationProperties(DataSourceProperties.class)
public class DevDataSourceConfig {

  private static final Logger log = LoggerFactory.getLogger(DevDataSourceConfig.class);

  @Bean
  public DataSource dataSource(DataSourceProperties properties) {
    log.info("Creating dev DataSource (direct JDBC) for url={}", properties.getUrl());

    return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void verifyConnection(ApplicationReadyEvent event) {
    DataSource dataSource = event.getApplicationContext().getBean(DataSource.class);
    DataSourceProperties properties =
        event.getApplicationContext().getBean(DataSourceProperties.class);

    if (!StringUtils.hasText(properties.getPassword())) {
      log.warn("DB_PASSWORD is not set. Configure it to connect to Oracle in local dev.");
      return;
    }

    try (Connection connection = dataSource.getConnection()) {
      log.info(
          "Database connection verified. url={} user={}",
          connection.getMetaData().getURL(),
          connection.getMetaData().getUserName());
    } catch (Exception ex) {
      log.warn(
          "Database connection failed. Check DB_URL, DB_USERNAME and DB_PASSWORD. Cause: {}",
          ex.getMessage());
    }
  }
}
