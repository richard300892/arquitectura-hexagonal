package com.alediesme.joyeria.security.model;

import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.Username;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class AuthenticatedUser {

  private final Username username;
  private final Set<Role> roles;

  public AuthenticatedUser(Username username, Set<Role> roles) {
    this.username = username;
    this.roles = Collections.unmodifiableSet(new HashSet<>(roles));
  }

  public Username getUsername() {
    return username;
  }

  public Set<Role> getRoles() {
    return roles;
  }
}
