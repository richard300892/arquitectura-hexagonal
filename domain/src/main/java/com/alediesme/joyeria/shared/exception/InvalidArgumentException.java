package com.alediesme.joyeria.shared.exception;

public class InvalidArgumentException extends DomainException {

  public InvalidArgumentException(String message) {
    super(ErrorCode.INVALID_ARGUMENT, message);
  }
}
