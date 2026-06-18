package com.alediesme.joyeria.security.model;

import com.alediesme.joyeria.security.valueobject.PasswordHash;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.UserId;
import com.alediesme.joyeria.security.valueobject.Username;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class User {

  private final UserId id;
  private final Username username;
  private final PasswordHash passwordHash;
  private final Set<Role> roles;
  private final boolean enabled;

  public User(
      UserId id, Username username, PasswordHash passwordHash, Set<Role> roles, boolean enabled) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.roles = Collections.unmodifiableSet(new HashSet<>(roles));
    this.enabled = enabled;
  }

  public UserId getId() {
    return id;
  }

  public Username getUsername() {
    return username;
  }

  public PasswordHash getPasswordHash() {
    return passwordHash;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean hasRole(Role role) {
    return roles.contains(role);
  }
}
