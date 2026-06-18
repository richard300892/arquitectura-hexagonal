package com.alediesme.joyeria.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jndi.JndiLookupFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private final ApiErrorMapper apiErrorMapper;

  public GlobalExceptionHandler(ApiErrorMapper apiErrorMapper) {
    this.apiErrorMapper = apiErrorMapper;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(
      AccessDeniedException exception, HttpServletRequest request) {
    log.warn(
        "Access denied path={} message={}", request.getRequestURI(), exception.getMessage());
    return apiErrorMapper.toAccessDeniedResponse(request);
  }

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorResponse> handleDomainException(
      DomainException exception, HttpServletRequest request) {
    logDomainException(exception, request);
    return apiErrorMapper.toResponse(exception, request);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ErrorResponse> handleApplicationException(
      ApplicationException exception, HttpServletRequest request) {
    log.warn(
        "Application error code={} path={} message={}",
        exception.getCode(),
        request.getRequestURI(),
        exception.getMessage());
    return apiErrorMapper.toResponse(exception, request);
  }

  @ExceptionHandler(InfrastructureException.class)
  public ResponseEntity<ErrorResponse> handleInfrastructureException(
      InfrastructureException exception, HttpServletRequest request) {
    log.error(
        "Infrastructure error code={} path={} message={}",
        exception.getCode(),
        request.getRequestURI(),
        exception.getMessage(),
        exception);
    return apiErrorMapper.toResponse(exception, request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    log.info(
        "Validation error path={} message={}", request.getRequestURI(), exception.getMessage());
    return apiErrorMapper.toValidationResponse(exception, request);
  }

  @ExceptionHandler({DataAccessException.class, JndiLookupFailureException.class})
  public ResponseEntity<ErrorResponse> handleDatabaseAccessException(
      Exception exception, HttpServletRequest request) {
    return handleInfrastructureException(toDatabaseAccessException(exception), request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedException(
      Exception exception, HttpServletRequest request) {
    log.error(
        "Unexpected error method={} path={} type={} message={}",
        request.getMethod(),
        request.getRequestURI(),
        exception.getClass().getName(),
        exception.getMessage(),
        exception);
    return apiErrorMapper.toUnexpectedResponse(request);
  }

  private void logDomainException(DomainException exception, HttpServletRequest request) {
    if (ErrorCode.AUTH_INVALID_CREDENTIALS.equals(exception.getCode())
        || ErrorCode.AUTH_INVALID_TOKEN.equals(exception.getCode())) {
      log.warn(
          "Auth error code={} path={} message={}",
          exception.getCode(),
          request.getRequestURI(),
          exception.getMessage());
      return;
    }

    log.info(
        "Domain error code={} path={} message={}",
        exception.getCode(),
        request.getRequestURI(),
        exception.getMessage());
  }

  private InfrastructureException toDatabaseAccessException(Exception exception) {
    return new InfrastructureException(
        InfrastructureErrorCode.DATABASE_ACCESS, "Database operation failed", exception);
  }
}
