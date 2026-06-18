package com.alediesme.joyeria.shared.exception;

import com.alediesme.joyeria.customer.exception.CustomerAlreadyExistsException;
import com.alediesme.joyeria.security.exception.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiErrorMapperTest {

  private ApiErrorMapper apiErrorMapper;
  private MockHttpServletRequest request;

  @BeforeEach
  void setUp() {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    apiErrorMapper = new ApiErrorMapper(objectMapper);
    request = new MockHttpServletRequest("GET", "/customer/1");
  }

  @Test
  void mapsNotFoundTo404() {
    var response =
        apiErrorMapper.toResponse(new EntityNotFoundException("Customer", 1L), request);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(ErrorCode.NOT_FOUND, response.getBody().getCode());
  }

  @Test
  void mapsConflictTo409() {
    var response =
        apiErrorMapper.toResponse(
            new CustomerAlreadyExistsException("Duplicate customer"), request);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals(ErrorCode.CONFLICT, response.getBody().getCode());
  }

  @Test
  void mapsInvalidTokenTo401() {
    var response = apiErrorMapper.toResponse(new InvalidTokenException(), request);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals(ErrorCode.AUTH_INVALID_TOKEN, response.getBody().getCode());
  }

  @Test
  void mapsDatabaseAccessToSafeMessage() {
    var response =
        apiErrorMapper.toResponse(
            new InfrastructureException(
                InfrastructureErrorCode.DATABASE_ACCESS, "ORA-00942 internal detail"),
            request);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("A database error occurred", response.getBody().getMessage());
  }

  @Test
  void mapsApplicationUseCaseErrorTo422() {
    var response =
        apiErrorMapper.toResponse(
            new ApplicationException(ApplicationErrorCode.USE_CASE_ERROR, "Use case failed"),
            request);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    assertEquals(ApplicationErrorCode.USE_CASE_ERROR, response.getBody().getCode());
  }
}
