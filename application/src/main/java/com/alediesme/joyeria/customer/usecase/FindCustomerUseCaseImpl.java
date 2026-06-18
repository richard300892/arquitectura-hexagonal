package com.alediesme.joyeria.customer.usecase;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.FindCustomerQuery;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.port.in.FindCustomerUseCase;
import com.alediesme.joyeria.customer.service.CustomerDomainService;

public final class FindCustomerUseCaseImpl implements FindCustomerUseCase {

  private final CustomerDomainService customerDomainService;

  public FindCustomerUseCaseImpl(CustomerDomainService customerDomainService) {
    this.customerDomainService = customerDomainService;
  }

  @Override
  public CustomerResponse execute(FindCustomerQuery query) {
    Customer customer = customerDomainService.findById(query.getId());

    return CustomerResponse.from(customer);
  }
}
