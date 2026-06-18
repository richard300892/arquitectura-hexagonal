package com.alediesme.joyeria.customer.adapter.in.web;

import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerWebMapperTest {

  private final CustomerWebMapper mapper = new CustomerWebMapper();

  @Test
  void mapsRegisterCustomerRequestToCommand() {
    RegisterCustomerRequest request = new RegisterCustomerRequest();
    request.setDocumentTypeId(1L);
    request.setDocumentNumber("99887766");
    request.setFirstName("Ana");
    request.setLastName("Lopez");
    request.setEmail("ana@example.com");
    request.setAcceptsMarketing(true);
    request.setNotes("VIP");

    RegisterCustomerCommand command = mapper.toCommand(request);

    assertEquals(1L, command.getDocumentTypeId());
    assertEquals("99887766", command.getDocumentNumber());
    assertEquals("Ana", command.getFirstName());
    assertEquals("Lopez", command.getLastName());
    assertEquals("ana@example.com", command.getEmail());
    assertTrue(command.isAcceptsMarketing());
    assertEquals("VIP", command.getNotes());
  }
}
