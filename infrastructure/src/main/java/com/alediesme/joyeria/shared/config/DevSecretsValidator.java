package com.alediesme.joyeria.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Profile("dev")
public class DevSecretsValidator {

  public DevSecretsValidator(
      @Value("${spring.datasource.password:}") String dbPassword,
      @Value("${app.security.jwt.secret:}") String jwtSecret) {

    requireNonBlank(dbPassword, "DB_PASSWORD");
    requireNonBlank(jwtSecret, "JWT_SECRET");

    if (jwtSecret.length() < 32) {
      throw new IllegalStateException("JWT_SECRET must be at least 32 characters in development");
    }
  }

  private void requireNonBlank(String value, String envName) {
    if (!StringUtils.hasText(value)) {
      throw new IllegalStateException(envName + " is required when spring.profiles.active=dev");
    }
  }
}
