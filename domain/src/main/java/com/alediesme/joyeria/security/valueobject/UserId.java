package com.alediesme.joyeria.security.valueobject;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.util.Objects;

public final class UserId {

  private final Long value;

  private UserId(Long value) {
    if (value == null) {
      throw new InvalidArgumentException("User id is required");
    }
    this.value = value;
  }

  public static UserId of(Long value) {
    return new UserId(value);
  }

  public Long value() {
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
    UserId userId = (UserId) object;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
