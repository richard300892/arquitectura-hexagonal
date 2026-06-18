package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.shared.exception.ApiErrorMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

  private final ApiErrorMapper apiErrorMapper;

  public JsonAccessDeniedHandler(ApiErrorMapper apiErrorMapper) {
    this.apiErrorMapper = apiErrorMapper;
  }

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    apiErrorMapper.writeAccessDenied(response, request);
  }
}
