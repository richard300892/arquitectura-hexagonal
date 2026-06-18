package com.alediesme.joyeria.security.port.in;

import com.alediesme.joyeria.security.model.AuthenticatedUser;

public interface ValidateTokenUseCase {

  AuthenticatedUser execute(String token);
}
