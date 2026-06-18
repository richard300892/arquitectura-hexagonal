package com.alediesme.joyeria.customer.port.out;

import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

  Optional<Customer> findById(CustomerId id);

  boolean existsByDocument(Long documentTypeId, String documentNumber);

  boolean existsByEmail(String email);

  Customer save(CustomerRegistration registration);
}
