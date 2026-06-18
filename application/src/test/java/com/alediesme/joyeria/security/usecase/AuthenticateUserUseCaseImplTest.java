package com.alediesme.joyeria.security.usecase;

import com.alediesme.joyeria.security.dto.AuthTokenResponse;
import com.alediesme.joyeria.security.dto.LoginCommand;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.out.TokenProvider;
import com.alediesme.joyeria.security.service.AuthenticationDomainService;
import com.alediesme.joyeria.security.valueobject.PasswordHash;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.UserId;
import com.alediesme.joyeria.security.valueobject.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseImplTest {

  @Mock private AuthenticationDomainService authenticationDomainService;

  @Mock private TokenProvider tokenProvider;

  @Test
  void executeReturnsTokenResponse() {
    User user =
        new User(
            UserId.of(1L),
            Username.of("admin"),
            PasswordHash.of("hash"),
            Set.of(Role.of("ROLE_ADMIN")),
            true);

    when(authenticationDomainService.authenticate("admin", "admin123")).thenReturn(user);
    when(tokenProvider.generate(user)).thenReturn("jwt-token");

    AuthenticateUserUseCaseImpl authenticateUserUseCase =
        new AuthenticateUserUseCaseImpl(authenticationDomainService, tokenProvider, 3600000L);

    AuthTokenResponse response =
        authenticateUserUseCase.execute(new LoginCommand("admin", "admin123"));

    Assertions.assertEquals("jwt-token", response.getAccessToken());
    Assertions.assertEquals("Bearer", response.getTokenType());
    Assertions.assertEquals(3600000L, response.getExpiresInMs());
    Assertions.assertEquals("admin", response.getUsername());
    Assertions.assertTrue(response.getRoles().contains("ROLE_ADMIN"));
  }
}
