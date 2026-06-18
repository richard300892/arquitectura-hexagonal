package com.alediesme.joyeria.shared.persistence.jdbc.sql;

public final class SqlDialect {

  public static final String COMMON = "common";
  public static final String ORACLE = "oracle";
  public static final String H2 = "h2";

  private SqlDialect() {}

  public static String normalize(String dialect) {
    if (dialect == null || dialect.isBlank()) {
      return COMMON;
    }
    return dialect.trim().toLowerCase();
  }
}
