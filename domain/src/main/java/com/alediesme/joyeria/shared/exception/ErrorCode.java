package com.alediesme.joyeria.shared.exception;

public final class ErrorCode {

  public static final String NOT_FOUND = "NOT_FOUND";
  public static final String INVALID_ARGUMENT = "INVALID_ARGUMENT";
  public static final String AUTH_INVALID_CREDENTIALS = "AUTH_INVALID_CREDENTIALS";
  public static final String AUTH_INVALID_TOKEN = "AUTH_INVALID_TOKEN";
  public static final String AUTH_USER_DISABLED = "AUTH_USER_DISABLED";
  public static final String AUTH_ACCESS_DENIED = "AUTH_ACCESS_DENIED";
  public static final String CONFLICT = "CONFLICT";

  private ErrorCode() {}
}
