package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.shared.exception.ApiErrorMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ApiErrorMapper apiErrorMapper;

  public JsonAuthenticationEntryPoint(ApiErrorMapper apiErrorMapper) {
    this.apiErrorMapper = apiErrorMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    apiErrorMapper.writeAuthenticationRequired(response, request);
  }
}
