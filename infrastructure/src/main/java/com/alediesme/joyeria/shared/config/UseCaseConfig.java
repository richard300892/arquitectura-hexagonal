package com.alediesme.joyeria.shared.config;

import com.alediesme.joyeria.customer.port.in.FindCustomerUseCase;
import com.alediesme.joyeria.customer.port.in.RegisterCustomerUseCase;
import com.alediesme.joyeria.customer.service.CustomerDomainService;
import com.alediesme.joyeria.customer.usecase.FindCustomerUseCaseImpl;
import com.alediesme.joyeria.customer.usecase.RegisterCustomerUseCaseImpl;
import com.alediesme.joyeria.security.port.in.AuthenticateUserUseCase;
import com.alediesme.joyeria.security.port.in.ValidateTokenUseCase;
import com.alediesme.joyeria.security.port.out.TokenProvider;
import com.alediesme.joyeria.security.service.AuthenticationDomainService;
import com.alediesme.joyeria.security.usecase.AuthenticateUserUseCaseImpl;
import com.alediesme.joyeria.security.usecase.ValidateTokenUseCaseImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

  @Bean
  public AuthenticateUserUseCase authenticateUserUseCase(
      AuthenticationDomainService authenticationDomainService,
      TokenProvider tokenProvider,
      @Value("${app.security.jwt.expiration-ms:3600000}") long jwtExpirationMs) {
    return new AuthenticateUserUseCaseImpl(
        authenticationDomainService, tokenProvider, jwtExpirationMs);
  }

  @Bean
  public ValidateTokenUseCase validateTokenUseCase(TokenProvider tokenProvider) {
    return new ValidateTokenUseCaseImpl(tokenProvider);
  }

  @Bean
  public FindCustomerUseCase findCustomerUseCase(CustomerDomainService customerDomainService) {
    return new FindCustomerUseCaseImpl(customerDomainService);
  }

  @Bean
  public RegisterCustomerUseCase registerCustomerUseCase(
      CustomerDomainService customerDomainService) {
    return new RegisterCustomerUseCaseImpl(customerDomainService);
  }
}
