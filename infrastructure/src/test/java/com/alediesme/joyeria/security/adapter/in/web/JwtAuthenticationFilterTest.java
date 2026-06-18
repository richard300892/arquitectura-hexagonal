package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.security.exception.InvalidTokenException;
import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.port.in.ValidateTokenUseCase;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.Username;
import com.alediesme.joyeria.shared.exception.ServletDomainExceptionWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

  @Mock private ValidateTokenUseCase validateTokenUseCase;
  @Mock private ServletDomainExceptionWriter servletDomainExceptionWriter;
  @Mock private FilterChain filterChain;

  private JwtAuthenticationFilter filter;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.clearContext();
    filter = new JwtAuthenticationFilter(validateTokenUseCase, servletDomainExceptionWriter);
  }

  @Test
  void continuesChainWhenAuthorizationHeaderIsMissing() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(servletDomainExceptionWriter, never()).write(any(), any(), any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void setsAuthenticationWhenTokenIsValid() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid-token");
    MockHttpServletResponse response = new MockHttpServletResponse();

    when(validateTokenUseCase.execute("valid-token"))
        .thenReturn(new AuthenticatedUser(Username.of("admin"), Set.of(Role.of("ROLE_ADMIN"))));

    filter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    verify(servletDomainExceptionWriter, never()).write(any(), any(), any());
  }

  @Test
  void writesErrorAndStopsChainWhenTokenIsInvalid() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/customer/1");
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid-token");
    MockHttpServletResponse response = new MockHttpServletResponse();
    InvalidTokenException exception = new InvalidTokenException();

    when(validateTokenUseCase.execute("invalid-token")).thenThrow(exception);

    filter.doFilterInternal(request, response, filterChain);

    verify(servletDomainExceptionWriter).write(eq(response), eq(request), eq(exception));
    verify(filterChain, never()).doFilter(any(), any());
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }
}
