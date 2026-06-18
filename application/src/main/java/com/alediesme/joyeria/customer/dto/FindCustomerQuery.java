package com.alediesme.joyeria.customer.dto;

public final class FindCustomerQuery {

  private final Long id;

  public FindCustomerQuery(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
