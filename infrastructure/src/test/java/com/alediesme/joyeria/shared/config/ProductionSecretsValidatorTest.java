package com.alediesme.joyeria.shared.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductionSecretsValidatorTest {

    @Test
    void acceptsValidProductionSecrets() {
        Assertions.assertDoesNotThrow(() -> new ProductionSecretsValidator(
                "jwt-secret-with-at-least-32-characters",
                "jdbc/AlediesmeDS"
        ));
    }

    @Test
    void rejectsMissingJndiName() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () ->
                new ProductionSecretsValidator(
                        "jwt-secret-with-at-least-32-characters",
                        ""
                ));

        Assertions.assertTrue(exception.getMessage().contains("DB_JNDI_NAME"));
    }

    @Test
    void rejectsShortJwtSecret() {
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () ->
                new ProductionSecretsValidator(
                        "short-secret",
                        "jdbc/AlediesmeDS"
                ));

        Assertions.assertTrue(exception.getMessage().contains("JWT_SECRET"));
    }
}
