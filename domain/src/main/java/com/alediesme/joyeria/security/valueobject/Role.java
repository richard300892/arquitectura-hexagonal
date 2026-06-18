package com.alediesme.joyeria.security.valueobject;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.util.Objects;

public final class Role {

  private final String value;

  private Role(String value) {
    if (value == null || value.isBlank()) {
      throw new InvalidArgumentException("Role is required");
    }
    this.value = value.trim().toUpperCase();
  }

  public static Role of(String value) {
    return new Role(value);
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Role role = (Role) object;
    return Objects.equals(value, role.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
