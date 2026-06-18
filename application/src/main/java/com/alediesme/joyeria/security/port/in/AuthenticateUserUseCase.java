package com.alediesme.joyeria.security.port.in;

import com.alediesme.joyeria.security.dto.AuthTokenResponse;
import com.alediesme.joyeria.security.dto.LoginCommand;

public interface AuthenticateUserUseCase {

  AuthTokenResponse execute(LoginCommand command);
}
