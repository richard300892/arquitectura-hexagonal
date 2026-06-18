package com.alediesme.joyeria.security.exception;

import com.alediesme.joyeria.shared.exception.DomainException;
import com.alediesme.joyeria.shared.exception.ErrorCode;

public class InvalidCredentialsException extends DomainException {

  public InvalidCredentialsException() {
    super(ErrorCode.AUTH_INVALID_CREDENTIALS, "Invalid username or password");
  }
}
