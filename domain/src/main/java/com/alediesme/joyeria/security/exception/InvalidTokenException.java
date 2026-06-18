package com.alediesme.joyeria.security.exception;

import com.alediesme.joyeria.shared.exception.DomainException;
import com.alediesme.joyeria.shared.exception.ErrorCode;

public class InvalidTokenException extends DomainException {

  public InvalidTokenException() {
    super(ErrorCode.AUTH_INVALID_TOKEN, "Invalid or expired token");
  }
}
