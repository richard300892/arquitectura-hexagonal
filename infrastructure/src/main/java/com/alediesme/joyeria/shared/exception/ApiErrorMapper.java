package com.alediesme.joyeria.shared.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class ApiErrorMapper {

  private final ObjectMapper objectMapper;

  public ApiErrorMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public ResponseEntity<ErrorResponse> toResponse(
      DomainException exception, HttpServletRequest request) {
    HttpStatus status = resolveDomainStatus(exception.getCode());
    ErrorResponse body =
        ErrorResponse.of(
            exception.getCode(), exception.getMessage(), status.value(), request.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }

  public ResponseEntity<ErrorResponse> toResponse(
      ApplicationException exception, HttpServletRequest request) {
    HttpStatus status = resolveApplicationStatus(exception.getCode());
    ErrorResponse body =
        ErrorResponse.of(
            exception.getCode(), exception.getMessage(), status.value(), request.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }

  public ResponseEntity<ErrorResponse> toResponse(
      InfrastructureException exception, HttpServletRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorResponse body =
        ErrorResponse.of(
            exception.getCode(),
            toClientMessage(exception),
            status.value(),
            request.getRequestURI());
    return ResponseEntity.status(status).body(body);
  }

  public ResponseEntity<ErrorResponse> toValidationResponse(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    String message =
        exception.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ErrorResponse body =
        ErrorResponse.of(
            InfrastructureErrorCode.VALIDATION,
            message,
            HttpStatus.BAD_REQUEST.value(),
            request.getRequestURI());
    return ResponseEntity.badRequest().body(body);
  }

  public ResponseEntity<ErrorResponse> toUnexpectedResponse(HttpServletRequest request) {
    ErrorResponse body =
        ErrorResponse.of(
            InfrastructureErrorCode.UNEXPECTED,
            "An unexpected error occurred",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

  public void writeJsonError(
      HttpServletResponse response, DomainException exception, HttpServletRequest request)
      throws IOException {
    ResponseEntity<ErrorResponse> entity = toResponse(exception, request);
    response.setStatus(entity.getStatusCodeValue());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), entity.getBody());
  }

  public void writeAuthenticationRequired(
      HttpServletResponse response, HttpServletRequest request) throws IOException {
    ErrorResponse body =
        ErrorResponse.of(
            ErrorCode.AUTH_INVALID_CREDENTIALS,
            "Authentication is required",
            HttpStatus.UNAUTHORIZED.value(),
            request.getRequestURI());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), body);
  }

  public void writeAccessDenied(HttpServletResponse response, HttpServletRequest request)
      throws IOException {
    ErrorResponse body = accessDeniedBody(request);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), body);
  }

  public ResponseEntity<ErrorResponse> toAccessDeniedResponse(HttpServletRequest request) {
    ErrorResponse body = accessDeniedBody(request);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
  }

  private ErrorResponse accessDeniedBody(HttpServletRequest request) {
    return ErrorResponse.of(
        ErrorCode.AUTH_ACCESS_DENIED,
        "Access is denied",
        HttpStatus.FORBIDDEN.value(),
        request.getRequestURI());
  }

  private HttpStatus resolveDomainStatus(String code) {
    return HttpStatusByErrorCode.forDomainCode(code);
  }

  private HttpStatus resolveApplicationStatus(String code) {
    return HttpStatusByErrorCode.forApplicationCode(code);
  }

  private String toClientMessage(InfrastructureException exception) {
    return HttpStatusByErrorCode.clientMessageForInfrastructure(exception.getCode());
  }
}
