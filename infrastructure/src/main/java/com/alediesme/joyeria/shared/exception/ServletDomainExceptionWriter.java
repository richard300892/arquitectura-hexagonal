package com.alediesme.joyeria.shared.exception;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServletDomainExceptionWriter {

  private final ApiErrorMapper apiErrorMapper;

  public ServletDomainExceptionWriter(ApiErrorMapper apiErrorMapper) {
    this.apiErrorMapper = apiErrorMapper;
  }

  public void write(
      HttpServletResponse response, HttpServletRequest request, DomainException exception)
      throws IOException {
    apiErrorMapper.writeJsonError(response, exception, request);
  }
}
