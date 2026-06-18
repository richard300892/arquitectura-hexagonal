package com.alediesme.joyeria.customer.usecase;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.model.CustomerRegistration;
import com.alediesme.joyeria.customer.service.CustomerDomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCustomerUseCaseImplTest {

  @Mock private CustomerDomainService customerDomainService;

  @Test
  void executeRegistersCustomerAndReturnsResponse() {
    RegisterCustomerCommand command =
        new RegisterCustomerCommand(
            1L,
            "9876543210",
            "Carlos",
            "Ruiz",
            "carlos.ruiz@example.com",
            null,
            "3009876543",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null);

    Customer saved =
        Customer.restore(
            2L,
            null,
            1L,
            "9876543210",
            "Carlos",
            "Ruiz",
            "carlos.ruiz@example.com",
            null,
            "3009876543",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            true);

    when(customerDomainService.register(any(CustomerRegistration.class))).thenReturn(saved);

    RegisterCustomerUseCaseImpl useCase = new RegisterCustomerUseCaseImpl(customerDomainService);
    CustomerResponse response = useCase.execute(command);

    ArgumentCaptor<CustomerRegistration> captor = ArgumentCaptor.forClass(CustomerRegistration.class);
    verify(customerDomainService).register(captor.capture());

    Assertions.assertEquals("9876543210", captor.getValue().getDocumentNumber());
    Assertions.assertEquals(2L, response.getId());
    Assertions.assertEquals("Carlos", response.getFirstName());
  }
}
