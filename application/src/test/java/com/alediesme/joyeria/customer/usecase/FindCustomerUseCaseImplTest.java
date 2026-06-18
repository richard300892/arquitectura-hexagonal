package com.alediesme.joyeria.customer.usecase;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.FindCustomerQuery;
import com.alediesme.joyeria.customer.model.Customer;
import com.alediesme.joyeria.customer.service.CustomerDomainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCustomerUseCaseImplTest {

  @Mock private CustomerDomainService customerDomainService;

  @Test
  void executeReturnsCustomerResponse() {
    Customer customer =
        Customer.restore(
            1L,
            null,
            1L,
            "1234567890",
            "Maria",
            "Gomez",
            "maria.gomez@example.com",
            "6041234567",
            "3001234567",
            null,
            null,
            LocalDate.of(1990, 5, 15),
            LocalDate.of(2015, 6, 20),
            null,
            "Calle 10 # 20-30",
            null,
            "050001",
            null,
            true,
            "Prefiere oro 18K",
            true);

    when(customerDomainService.findById(1L)).thenReturn(customer);

    FindCustomerUseCaseImpl useCase = new FindCustomerUseCaseImpl(customerDomainService);
    CustomerResponse response = useCase.execute(new FindCustomerQuery(1L));

    Assertions.assertEquals(1L, response.getId());
    Assertions.assertEquals("Maria", response.getFirstName());
    Assertions.assertEquals("1234567890", response.getDocumentNumber());
  }
}
