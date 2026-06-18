package com.alediesme.joyeria.customer.port.in;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.RegisterCustomerCommand;

public interface RegisterCustomerUseCase {

  CustomerResponse execute(RegisterCustomerCommand command);
}
