package com.alediesme.joyeria.security.adapter.out.persistence.jdbc;

public class UserRecord {

  private final Long id;
  private final String username;
  private final String passwordHash;
  private final boolean enabled;

  public UserRecord(Long id, String username, String passwordHash, boolean enabled) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
