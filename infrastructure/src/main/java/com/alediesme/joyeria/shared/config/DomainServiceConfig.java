package com.alediesme.joyeria.shared.config;

import com.alediesme.joyeria.customer.port.out.CustomerRepository;
import com.alediesme.joyeria.customer.service.CustomerDomainService;
import com.alediesme.joyeria.security.port.out.PasswordHasher;
import com.alediesme.joyeria.security.port.out.UserRepository;
import com.alediesme.joyeria.security.service.AuthenticationDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {

  @Bean
  public AuthenticationDomainService authenticationDomainService(
      UserRepository userRepository, PasswordHasher passwordHasher) {
    return new AuthenticationDomainService(userRepository, passwordHasher);
  }

  @Bean
  public CustomerDomainService customerDomainService(CustomerRepository customerRepository) {
    return new CustomerDomainService(customerRepository);
  }
}
