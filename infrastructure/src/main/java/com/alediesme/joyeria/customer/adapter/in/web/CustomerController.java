package com.alediesme.joyeria.customer.adapter.in.web;

import com.alediesme.joyeria.customer.dto.CustomerResponse;
import com.alediesme.joyeria.customer.dto.FindCustomerQuery;
import com.alediesme.joyeria.customer.port.in.FindCustomerUseCase;
import com.alediesme.joyeria.customer.port.in.RegisterCustomerUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Tag(name = "Customer API")
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {

  private final FindCustomerUseCase findCustomerUseCase;
  private final RegisterCustomerUseCase registerCustomerUseCase;
  private final CustomerWebMapper customerWebMapper;

  public CustomerController(
      FindCustomerUseCase findCustomerUseCase,
      RegisterCustomerUseCase registerCustomerUseCase,
      CustomerWebMapper customerWebMapper) {
    this.findCustomerUseCase = findCustomerUseCase;
    this.registerCustomerUseCase = registerCustomerUseCase;
    this.customerWebMapper = customerWebMapper;
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER_WEB')")
  public CustomerResponse getById(@PathVariable Long id) {
    return findCustomerUseCase.execute(new FindCustomerQuery(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public CustomerResponse register(@Valid @RequestBody RegisterCustomerRequest request) {
    return registerCustomerUseCase.execute(customerWebMapper.toCommand(request));
  }
}
