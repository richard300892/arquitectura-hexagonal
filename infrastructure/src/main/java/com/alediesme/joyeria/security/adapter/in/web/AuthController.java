package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.security.dto.AuthTokenResponse;
import com.alediesme.joyeria.security.dto.LoginCommand;
import com.alediesme.joyeria.security.port.in.AuthenticateUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API")
public class AuthController {

  private final AuthenticateUserUseCase authenticateUserUseCase;

  public AuthController(AuthenticateUserUseCase authenticateUserUseCase) {
    this.authenticateUserUseCase = authenticateUserUseCase;
  }

  @PostMapping("/login")
  public AuthTokenResponse login(@Valid @RequestBody LoginRequest request) {
    return authenticateUserUseCase.execute(
        new LoginCommand(request.getUsername(), request.getPassword()));
  }
}
