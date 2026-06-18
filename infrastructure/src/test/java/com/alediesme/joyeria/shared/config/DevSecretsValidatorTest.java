package com.alediesme.joyeria.shared.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DevSecretsValidatorTest {

  @Test
  void acceptsValidDevelopmentSecrets() {
    Assertions.assertDoesNotThrow(() -> new DevSecretsValidator(
        "db-password",
        "jwt-secret-with-at-least-32-characters"
    ));
  }

  @Test
  void rejectsMissingDbPassword() {
    IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () ->
        new DevSecretsValidator(
            "",
            "jwt-secret-with-at-least-32-characters"
        ));

    Assertions.assertTrue(exception.getMessage().contains("DB_PASSWORD"));
  }

  @Test
  void rejectsShortJwtSecret() {
    IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () ->
        new DevSecretsValidator(
            "db-password",
            "short-secret"
        ));

    Assertions.assertTrue(exception.getMessage().contains("JWT_SECRET"));
  }
}
