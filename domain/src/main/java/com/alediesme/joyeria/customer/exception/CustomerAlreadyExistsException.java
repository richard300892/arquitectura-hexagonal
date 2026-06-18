package com.alediesme.joyeria.customer.exception;

import com.alediesme.joyeria.shared.exception.DomainException;
import com.alediesme.joyeria.shared.exception.ErrorCode;

public class CustomerAlreadyExistsException extends DomainException {

  public CustomerAlreadyExistsException(String message) {
    super(ErrorCode.CONFLICT, message);
  }
}
