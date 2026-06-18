package com.alediesme.joyeria.shared.config.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestCorrelationFilterTest {

  private final RequestCorrelationFilter filter = new RequestCorrelationFilter();

  @AfterEach
  void tearDown() {
    MDC.clear();
  }

  @Test
  void reusesIncomingRequestIdHeader() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    request.addHeader(RequestCorrelationFilter.HEADER_NAME, "trace-123");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    assertEquals("trace-123", response.getHeader(RequestCorrelationFilter.HEADER_NAME));
    assertNull(MDC.get(RequestCorrelationFilter.MDC_KEY));
  }

  @Test
  void generatesRequestIdWhenHeaderMissing() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    String requestId = response.getHeader(RequestCorrelationFilter.HEADER_NAME);
    assertNotNull(requestId);
    assertTrue(requestId.length() >= 32);
    assertNull(MDC.get(RequestCorrelationFilter.MDC_KEY));
  }
}
