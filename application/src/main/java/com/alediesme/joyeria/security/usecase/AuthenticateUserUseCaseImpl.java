package com.alediesme.joyeria.security.usecase;

import com.alediesme.joyeria.security.dto.AuthTokenResponse;
import com.alediesme.joyeria.security.dto.LoginCommand;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.in.AuthenticateUserUseCase;
import com.alediesme.joyeria.security.port.out.TokenProvider;
import com.alediesme.joyeria.security.service.AuthenticationDomainService;
import com.alediesme.joyeria.security.valueobject.Role;

import java.util.stream.Collectors;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

  private final AuthenticationDomainService authenticationDomainService;
  private final TokenProvider tokenProvider;
  private final long expirationMs;

  public AuthenticateUserUseCaseImpl(
      AuthenticationDomainService authenticationDomainService,
      TokenProvider tokenProvider,
      long expirationMs) {
    this.authenticationDomainService = authenticationDomainService;
    this.tokenProvider = tokenProvider;
    this.expirationMs = expirationMs;
  }

  @Override
  public AuthTokenResponse execute(LoginCommand command) {
    User user =
        authenticationDomainService.authenticate(command.getUsername(), command.getPassword());
    String token = tokenProvider.generate(user);

    return new AuthTokenResponse(
        token,
        "Bearer",
        expirationMs,
        user.getUsername().value(),
        user.getRoles().stream().map(Role::value).collect(Collectors.toSet()));
  }
}
