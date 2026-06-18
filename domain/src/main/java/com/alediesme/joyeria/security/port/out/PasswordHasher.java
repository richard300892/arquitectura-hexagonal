package com.alediesme.joyeria.security.port.out;

import com.alediesme.joyeria.security.valueobject.PasswordHash;

public interface PasswordHasher {

  boolean matches(String plainPassword, PasswordHash passwordHash);

  PasswordHash hash(String plainPassword);
}
