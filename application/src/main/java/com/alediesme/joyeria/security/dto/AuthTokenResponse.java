package com.alediesme.joyeria.security.dto;

import java.util.Set;

public final class AuthTokenResponse {

  private final String accessToken;
  private final String tokenType;
  private final long expiresInMs;
  private final String username;
  private final Set<String> roles;

  public AuthTokenResponse(
      String accessToken, String tokenType, long expiresInMs, String username, Set<String> roles) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresInMs = expiresInMs;
    this.username = username;
    this.roles = roles;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public long getExpiresInMs() {
    return expiresInMs;
  }

  public String getUsername() {
    return username;
  }

  public Set<String> getRoles() {
    return roles;
  }
}
