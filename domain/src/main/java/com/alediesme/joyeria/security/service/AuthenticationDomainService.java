package com.alediesme.joyeria.security.service;

import com.alediesme.joyeria.security.exception.InvalidCredentialsException;
import com.alediesme.joyeria.security.exception.UserDisabledException;
import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.out.PasswordHasher;
import com.alediesme.joyeria.security.port.out.UserRepository;
import com.alediesme.joyeria.security.valueobject.Username;

public class AuthenticationDomainService {

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  public AuthenticationDomainService(UserRepository userRepository, PasswordHasher passwordHasher) {
    this.userRepository = userRepository;
    this.passwordHasher = passwordHasher;
  }

  public User authenticate(String username, String plainPassword) {
    User user =
        userRepository
            .findByUsername(Username.of(username))
            .orElseThrow(InvalidCredentialsException::new);

    if (!user.isEnabled()) {
      throw new UserDisabledException();
    }

    if (!passwordHasher.matches(plainPassword, user.getPasswordHash())) {
      throw new InvalidCredentialsException();
    }

    return user;
  }
}
