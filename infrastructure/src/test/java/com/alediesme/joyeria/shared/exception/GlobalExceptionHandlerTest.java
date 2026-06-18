package com.alediesme.joyeria.shared.exception;

import com.alediesme.joyeria.customer.exception.CustomerAlreadyExistsException;
import com.alediesme.joyeria.security.exception.InvalidCredentialsException;
import com.alediesme.joyeria.security.exception.InvalidTokenException;
import com.alediesme.joyeria.security.exception.UserDisabledException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jndi.JndiLookupFailureException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    handler = new GlobalExceptionHandler(new ApiErrorMapper(objectMapper));
        request = new MockHttpServletRequest("GET", "/customer/1");
    }

    @Test
    void handleNotFoundReturns404() {
        var response = handler.handleDomainException(new EntityNotFoundException("Customer", 1L), request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(ErrorCode.NOT_FOUND, response.getBody().getCode());
        assertEquals("/customer/1", response.getBody().getPath());
    }

    @Test
    void handleConflictReturns409() {
        var response =
                handler.handleDomainException(
                        new CustomerAlreadyExistsException("A customer with the same document already exists"),
                        request);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals(ErrorCode.CONFLICT, response.getBody().getCode());
    }

    @Test
    void handleInvalidArgumentReturns400() {
        var response = handler.handleDomainException(new InvalidArgumentException("Invalid id"), request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.INVALID_ARGUMENT, response.getBody().getCode());
    }

    @Test
    void handleInvalidCredentialsReturns401() {
        var response = handler.handleDomainException(new InvalidCredentialsException(), request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals(ErrorCode.AUTH_INVALID_CREDENTIALS, response.getBody().getCode());
    }

    @Test
    void handleInvalidTokenReturns401() {
        var response = handler.handleDomainException(new InvalidTokenException(), request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals(ErrorCode.AUTH_INVALID_TOKEN, response.getBody().getCode());
    }

    @Test
    void handleUserDisabledReturns403() {
        var response = handler.handleDomainException(new UserDisabledException(), request);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals(ErrorCode.AUTH_USER_DISABLED, response.getBody().getCode());
    }

    @Test
    void handleInfrastructureReturns500WithSafeMessage() {
        var response = handler.handleInfrastructureException(
                new InfrastructureException(
                        InfrastructureErrorCode.DATABASE_ACCESS,
                        "ORA-00942 internal detail"),
                request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(InfrastructureErrorCode.DATABASE_ACCESS, response.getBody().getCode());
        assertEquals("A database error occurred", response.getBody().getMessage());
    }

    @Test
    void handleApplicationExceptionReturns422() {
        var response = handler.handleApplicationException(
                new ApplicationException(ApplicationErrorCode.USE_CASE_ERROR, "Use case failed"),
                request);

        assertEquals(422, response.getStatusCodeValue());
        assertEquals(ApplicationErrorCode.USE_CASE_ERROR, response.getBody().getCode());
    }

    @Test
    void handleJndiLookupFailureReturnsDatabaseAccess() {
        var response = handler.handleDatabaseAccessException(
                new JndiLookupFailureException("JNDI lookup failed", new NamingException("jdbc/AlediesmeDS not found")),
                request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(InfrastructureErrorCode.DATABASE_ACCESS, response.getBody().getCode());
        assertEquals("A database error occurred", response.getBody().getMessage());
    }

    @Test
    void handleAccessDeniedReturns403() {
        var response =
            handler.handleAccessDeniedException(new AccessDeniedException("Denied"), request);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals(ErrorCode.AUTH_ACCESS_DENIED, response.getBody().getCode());
    }

    @Test
    void handleUnexpectedReturns500() {
        var response = handler.handleUnexpectedException(new RuntimeException("JNDI failure"), request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(InfrastructureErrorCode.UNEXPECTED, response.getBody().getCode());
    }
}
