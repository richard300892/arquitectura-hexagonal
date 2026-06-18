package com.alediesme.joyeria.customer.usecase;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.port.in.RegisterCustomerUseCase;
import com.alediesme.joyeria.customer.service.CustomerDomainService;

public final class RegisterCustomerUseCaseImpl implements RegisterCustomerUseCase {

  private final CustomerDomainService customerDomainService;

  public RegisterCustomerUseCaseImpl(CustomerDomainService customerDomainService) {
    this.customerDomainService = customerDomainService;
  }

  @Override
  public CustomerResponse execute(RegisterCustomerCommand command) {
    CustomerRegistration registration =
        CustomerRegistration.of(
            command.getDocumentTypeId(),
            command.getDocumentNumber(),
            command.getFirstName(),
            command.getLastName(),
            command.getEmail(),
            command.getPhone(),
            command.getMobilePhone(),
            command.getGenderId(),
            command.getMaritalStatusId(),
            command.getBirthDate(),
            command.getAnniversaryDate(),
            command.getNeighborhoodId(),
            command.getAddressLine(),
            command.getAddressComplement(),
            command.getPostalCode(),
            command.getPreferredCurrencyId(),
            command.isAcceptsMarketing(),
            command.getNotes());

    Customer customer = customerDomainService.register(registration);

    return CustomerResponse.from(customer);
  }
}
