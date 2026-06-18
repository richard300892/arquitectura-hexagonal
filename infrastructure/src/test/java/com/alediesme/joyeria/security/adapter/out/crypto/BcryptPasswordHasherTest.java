package com.alediesme.joyeria.security.adapter.out.crypto;

import com.alediesme.joyeria.security.valueobject.PasswordHash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BcryptPasswordHasherTest {

    private final BcryptPasswordHasher passwordHasher = new BcryptPasswordHasher();

    @Test
    void hashAndMatchPassword() {
        PasswordHash hash = passwordHasher.hash("admin123");

        Assertions.assertTrue(passwordHasher.matches("admin123", hash));
        Assertions.assertFalse(passwordHasher.matches("wrong", hash));
    }
}
