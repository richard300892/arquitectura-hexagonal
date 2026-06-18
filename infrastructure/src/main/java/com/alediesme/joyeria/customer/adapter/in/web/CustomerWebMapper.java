package com.alediesme.joyeria.customer.adapter.in.web;

import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;
import org.springframework.stereotype.Component;

@Component
public class CustomerWebMapper {

  public RegisterCustomerCommand toCommand(RegisterCustomerRequest request) {
    return new RegisterCustomerCommand(
        request.getDocumentTypeId(),
        request.getDocumentNumber(),
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getPhone(),
        request.getMobilePhone(),
        request.getGenderId(),
        request.getMaritalStatusId(),
        request.getBirthDate(),
        request.getAnniversaryDate(),
        request.getNeighborhoodId(),
        request.getAddressLine(),
        request.getAddressComplement(),
        request.getPostalCode(),
        request.getPreferredCurrencyId(),
        request.isAcceptsMarketing(),
        request.getNotes());
  }
}
