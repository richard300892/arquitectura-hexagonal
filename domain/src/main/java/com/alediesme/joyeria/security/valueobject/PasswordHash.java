package com.alediesme.joyeria.security.valueobject;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.util.Objects;

public final class PasswordHash {

  private final String value;

  private PasswordHash(String value) {
    if (value == null || value.isBlank()) {
      throw new InvalidArgumentException("Password hash is required");
    }
    this.value = value;
  }

  public static PasswordHash of(String value) {
    return new PasswordHash(value);
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
    PasswordHash that = (PasswordHash) object;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
