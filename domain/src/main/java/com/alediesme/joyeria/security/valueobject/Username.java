package com.alediesme.joyeria.security.valueobject;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.util.Objects;

public final class Username {

  private final String value;

  private Username(String value) {
    if (value == null || value.isBlank()) {
      throw new InvalidArgumentException("Username is required");
    }
    this.value = value.trim();
  }

  public static Username of(String value) {
    return new Username(value);
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
    Username username = (Username) object;
    return Objects.equals(value, username.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
