package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.shared.exception.ApiErrorMapper;
import com.alediesme.joyeria.shared.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonAuthenticationEntryPointTest {

  private JsonAuthenticationEntryPoint entryPoint;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    entryPoint = new JsonAuthenticationEntryPoint(new ApiErrorMapper(objectMapper));
  }

  @Test
  void commenceWritesUnauthorizedJsonBody() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    MockHttpServletResponse response = new MockHttpServletResponse();

    entryPoint.commence(
        request, response, new InsufficientAuthenticationException("Full authentication required"));

    assertEquals(401, response.getStatus());
    assertTrue(response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
    assertTrue(response.getContentAsString().contains(ErrorCode.AUTH_INVALID_CREDENTIALS));
    assertTrue(response.getContentAsString().contains("Authentication is required"));
  }
}
