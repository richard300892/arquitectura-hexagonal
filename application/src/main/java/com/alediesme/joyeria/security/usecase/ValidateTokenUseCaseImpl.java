package com.alediesme.joyeria.security.usecase;

import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.port.in.ValidateTokenUseCase;
import com.alediesme.joyeria.security.port.out.TokenProvider;

public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase {

  private final TokenProvider tokenProvider;

  public ValidateTokenUseCaseImpl(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public AuthenticatedUser execute(String token) {
    return tokenProvider.parse(token);
  }
}
