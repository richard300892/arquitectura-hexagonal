package com.alediesme.joyeria.security.adapter.out.crypto;

import com.alediesme.joyeria.security.port.out.PasswordHasher;
import com.alediesme.joyeria.security.valueobject.PasswordHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasher implements PasswordHasher {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public boolean matches(String plainPassword, PasswordHash passwordHash) {
    return passwordEncoder.matches(plainPassword, passwordHash.value());
  }

  @Override
  public PasswordHash hash(String plainPassword) {
    return PasswordHash.of(passwordEncoder.encode(plainPassword));
  }
}
