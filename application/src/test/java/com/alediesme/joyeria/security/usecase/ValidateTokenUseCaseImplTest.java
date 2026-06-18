package com.alediesme.joyeria.security.usecase;

import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.port.out.TokenProvider;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.Username;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateTokenUseCaseImplTest {

  @Mock private TokenProvider tokenProvider;

  @Test
  void executeDelegatesToTokenProvider() {
    AuthenticatedUser authenticatedUser =
        new AuthenticatedUser(Username.of("admin"), Set.of(Role.of("ROLE_ADMIN")));

    when(tokenProvider.parse("jwt-token")).thenReturn(authenticatedUser);

    ValidateTokenUseCaseImpl useCase = new ValidateTokenUseCaseImpl(tokenProvider);
    AuthenticatedUser result = useCase.execute("jwt-token");

    Assertions.assertEquals("admin", result.getUsername().value());
    Assertions.assertTrue(result.getRoles().contains(Role.of("ROLE_ADMIN")));
  }
}
