package com.alediesme.joyeria.shared.persistence.jdbc.sql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class SqlStatementProcessor implements BeanPostProcessor {

  private final String sqlDialect;

  public SqlStatementProcessor(
      @Value("${app.persistence.sql-dialect:common}") String sqlDialect) {
    this.sqlDialect = sqlDialect;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) {
    ReflectionUtils.doWithFields(
        bean.getClass(), new SqlStatementFieldCallback(bean, sqlDialect));
    return bean;
  }
}
