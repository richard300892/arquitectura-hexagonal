package com.alediesme.joyeria.security.port.out;

import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.model.User;

public interface TokenProvider {

  String generate(User user);

  AuthenticatedUser parse(String token);
}
