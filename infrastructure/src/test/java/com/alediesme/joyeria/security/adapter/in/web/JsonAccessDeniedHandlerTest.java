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
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonAccessDeniedHandlerTest {

  private JsonAccessDeniedHandler accessDeniedHandler;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    accessDeniedHandler = new JsonAccessDeniedHandler(new ApiErrorMapper(objectMapper));
  }

  @Test
  void handleWritesForbiddenJsonBody() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/customer");
    MockHttpServletResponse response = new MockHttpServletResponse();

    accessDeniedHandler.handle(request, response, new AccessDeniedException("Denied"));

    assertEquals(403, response.getStatus());
    assertTrue(response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
    assertTrue(response.getContentAsString().contains(ErrorCode.AUTH_ACCESS_DENIED));
    assertTrue(response.getContentAsString().contains("Access is denied"));
  }
}
