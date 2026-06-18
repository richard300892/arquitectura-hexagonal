package com.alediesme.joyeria.security.adapter.in.web;

import com.alediesme.joyeria.security.model.AuthenticatedUser;
import com.alediesme.joyeria.security.port.in.ValidateTokenUseCase;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.shared.exception.DomainException;
import com.alediesme.joyeria.shared.exception.ServletDomainExceptionWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final ValidateTokenUseCase validateTokenUseCase;
  private final ServletDomainExceptionWriter servletDomainExceptionWriter;

  public JwtAuthenticationFilter(
      ValidateTokenUseCase validateTokenUseCase,
      ServletDomainExceptionWriter servletDomainExceptionWriter) {
    this.validateTokenUseCase = validateTokenUseCase;
    this.servletDomainExceptionWriter = servletDomainExceptionWriter;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);

      try {
        AuthenticatedUser authenticatedUser = validateTokenUseCase.execute(token);
        SecurityContextHolder.getContext().setAuthentication(toAuthentication(authenticatedUser));
      } catch (DomainException exception) {
        servletDomainExceptionWriter.write(response, request, exception);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken toAuthentication(AuthenticatedUser authenticatedUser) {
    return new UsernamePasswordAuthenticationToken(
        authenticatedUser.getUsername().value(),
        null,
        authenticatedUser.getRoles().stream()
            .map(Role::value)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
  }
}
