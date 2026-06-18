package com.alediesme.joyeria.shared.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

final class HttpStatusByErrorCode {

  private static final Map<String, HttpStatus> DOMAIN =
      Map.ofEntries(
          Map.entry(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND),
          Map.entry(ErrorCode.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST),
          Map.entry(ErrorCode.AUTH_INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED),
          Map.entry(ErrorCode.AUTH_INVALID_TOKEN, HttpStatus.UNAUTHORIZED),
          Map.entry(ErrorCode.AUTH_USER_DISABLED, HttpStatus.FORBIDDEN),
          Map.entry(ErrorCode.AUTH_ACCESS_DENIED, HttpStatus.FORBIDDEN),
          Map.entry(ErrorCode.CONFLICT, HttpStatus.CONFLICT));

  private static final Map<String, HttpStatus> APPLICATION =
      Map.of(ApplicationErrorCode.USE_CASE_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);

  private static final Map<String, String> INFRASTRUCTURE_CLIENT_MESSAGE =
      Map.of(
          InfrastructureErrorCode.DATABASE_ACCESS, "A database error occurred",
          InfrastructureErrorCode.SQL_STATEMENT, "A technical configuration error occurred",
          InfrastructureErrorCode.INFRASTRUCTURE, "A technical error occurred");

  private HttpStatusByErrorCode() {}

  static HttpStatus forDomainCode(String code) {
    return DOMAIN.getOrDefault(code, HttpStatus.BAD_REQUEST);
  }

  static HttpStatus forApplicationCode(String code) {
    return APPLICATION.getOrDefault(code, HttpStatus.BAD_REQUEST);
  }

  static String clientMessageForInfrastructure(String code) {
    return INFRASTRUCTURE_CLIENT_MESSAGE.getOrDefault(code, "A technical error occurred");
  }
}
