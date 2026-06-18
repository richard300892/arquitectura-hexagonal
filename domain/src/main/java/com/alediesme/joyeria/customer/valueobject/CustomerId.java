package com.alediesme.joyeria.customer.valueobject;

import com.alediesme.joyeria.shared.exception.InvalidArgumentException;

import java.util.Objects;

public final class CustomerId {

  private final Long value;

  private CustomerId(Long value) {
    if (value == null) {
      throw new InvalidArgumentException("Customer id is required");
    }

    this.value = value;
  }

  public static CustomerId of(Long value) {
    return new CustomerId(value);
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
    CustomerId customerId = (CustomerId) object;
    return Objects.equals(value, customerId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
