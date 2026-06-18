package com.alediesme.joyeria.customer.port.in;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.FindCustomerQuery;

public interface FindCustomerUseCase {

  CustomerResponse execute(FindCustomerQuery query);
}
