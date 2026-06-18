package com.alediesme.joyeria.shared.exception;

import java.time.Instant;

public final class ErrorResponse {

  private final String code;
  private final String message;
  private final int status;
  private final String path;
  private final Instant timestamp;

  private ErrorResponse(String code, String message, int status, String path, Instant timestamp) {
    this.code = code;
    this.message = message;
    this.status = status;
    this.path = path;
    this.timestamp = timestamp;
  }

  public static ErrorResponse of(String code, String message, int status, String path) {
    return new ErrorResponse(code, message, status, path, Instant.now());
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public String getPath() {
    return path;
  }

  public Instant getTimestamp() {
    return timestamp;
  }
}
