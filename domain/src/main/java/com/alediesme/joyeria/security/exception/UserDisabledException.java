package com.alediesme.joyeria.security.exception;

import com.alediesme.joyeria.shared.exception.DomainException;
import com.alediesme.joyeria.shared.exception.ErrorCode;

public class UserDisabledException extends DomainException {

  public UserDisabledException() {
    super(ErrorCode.AUTH_USER_DISABLED, "User account is disabled");
  }
}
