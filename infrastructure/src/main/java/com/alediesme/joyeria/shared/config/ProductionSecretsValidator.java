package com.alediesme.joyeria.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Profile("prod")
public class ProductionSecretsValidator {

  public ProductionSecretsValidator(
      @Value("${app.security.jwt.secret:}") String jwtSecret,
      @Value("${spring.datasource.jndi-name:}") String jndiName) {

    requireNonBlank(jndiName, "DB_JNDI_NAME (spring.datasource.jndi-name)");
    requireNonBlank(jwtSecret, "JWT_SECRET");

    if (jwtSecret.length() < 32) {
      throw new IllegalStateException("JWT_SECRET must be at least 32 characters in production");
    }
  }

  private void requireNonBlank(String value, String envName) {
    if (!StringUtils.hasText(value)) {
      throw new IllegalStateException(envName + " is required when spring.profiles.active=prod");
    }
  }
}
