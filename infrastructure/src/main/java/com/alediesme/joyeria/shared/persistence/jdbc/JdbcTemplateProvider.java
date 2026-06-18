package com.alediesme.joyeria.shared.persistence.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateProvider {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public JdbcTemplateProvider(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public NamedParameterJdbcTemplate getTemplate() {
    return namedParameterJdbcTemplate;
  }
}
