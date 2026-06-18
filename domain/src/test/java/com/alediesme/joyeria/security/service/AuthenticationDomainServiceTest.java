package com.alediesme.joyeria.security.service;

import com.alediesme.joyeria.security.exception.InvalidCredentialsException;
import com.alediesme.joyeria.security.exception.UserDisabledException;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.out.PasswordHasher;
import com.alediesme.joyeria.security.port.out.UserRepository;
import com.alediesme.joyeria.security.valueobject.PasswordHash;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.UserId;
import com.alediesme.joyeria.security.valueobject.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationDomainServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private AuthenticationDomainService authenticationDomainService;

    @Test
    void authenticateReturnsUserWhenCredentialsAreValid() {
        User user = sampleUser(true);
        when(userRepository.findByUsername(any(Username.class))).thenReturn(Optional.of(user));
        when(passwordHasher.matches("admin123", user.getPasswordHash())).thenReturn(true);

        User authenticated = authenticationDomainService.authenticate("admin", "admin123");

        Assertions.assertEquals("admin", authenticated.getUsername().value());
    }

    @Test
    void authenticateThrowsWhenUserNotFound() {
        when(userRepository.findByUsername(any(Username.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(
                InvalidCredentialsException.class,
                () -> authenticationDomainService.authenticate("admin", "admin123")
        );
    }

    @Test
    void authenticateThrowsWhenPasswordDoesNotMatch() {
        User user = sampleUser(true);
        when(userRepository.findByUsername(any(Username.class))).thenReturn(Optional.of(user));
        when(passwordHasher.matches("wrong", user.getPasswordHash())).thenReturn(false);

        Assertions.assertThrows(
                InvalidCredentialsException.class,
                () -> authenticationDomainService.authenticate("admin", "wrong")
        );
    }

    @Test
    void authenticateThrowsWhenUserIsDisabled() {
        User user = sampleUser(false);
        when(userRepository.findByUsername(any(Username.class))).thenReturn(Optional.of(user));

        Assertions.assertThrows(
                UserDisabledException.class,
                () -> authenticationDomainService.authenticate("admin", "admin123")
        );
    }

    private User sampleUser(boolean enabled) {
        return new User(
                UserId.of(1L),
                Username.of("admin"),
                PasswordHash.of("hash"),
                Set.of(Role.of("ROLE_ADMIN")),
                enabled
        );
    }
}
