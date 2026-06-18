package com.alediesme.joyeria.customer.service;

import com.alediesme.joyeria.customer.exception.CustomerAlreadyExistsException;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.port.out.CustomerRepository;
import com.alediesme.joyeria.customer.valueobject.CustomerId;
import com.alediesme.joyeria.shared.exception.EntityNotFoundException;

public class CustomerDomainService {

  private final CustomerRepository customerRepository;

  public CustomerDomainService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer findById(Long id) {
    CustomerId customerId = CustomerId.of(id);

    return customerRepository
        .findById(customerId)
        .orElseThrow(() -> new EntityNotFoundException("Customer", id));
  }

  public Customer register(CustomerRegistration registration) {
    if (customerRepository.existsByDocument(
        registration.getDocumentTypeId(), registration.getDocumentNumber())) {
      throw new CustomerAlreadyExistsException("A customer with the same document already exists");
    }

    if (registration.getEmail() != null
        && customerRepository.existsByEmail(registration.getEmail())) {
      throw new CustomerAlreadyExistsException("A customer with the same email already exists");
    }

    return customerRepository.save(registration);
  }
}
