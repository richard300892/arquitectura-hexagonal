package com.alediesme.joyeria.security.adapter.out.jwt;

import com.alediesme.joyeria.security.exception.InvalidTokenException;
import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.valueobject.PasswordHash;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.UserId;
import com.alediesme.joyeria.security.valueobject.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("alediesme-local-dev-jwt-secret-key-32b");
        jwtProperties.setExpirationMs(3600000L);
        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    void generateAndParseToken() {
        User user = new User(
                UserId.of(1L),
                Username.of("admin"),
                PasswordHash.of("hash"),
                Set.of(Role.of("ROLE_ADMIN")),
                true
        );

        String token = jwtTokenProvider.generate(user);
        AuthenticatedUser authenticatedUser = jwtTokenProvider.parse(token);

        Assertions.assertEquals("admin", authenticatedUser.getUsername().value());
        Assertions.assertTrue(authenticatedUser.getRoles().contains(Role.of("ROLE_ADMIN")));
    }

    @Test
    void parseInvalidTokenThrows() {
        Assertions.assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.parse("invalid-token"));
    }
}
