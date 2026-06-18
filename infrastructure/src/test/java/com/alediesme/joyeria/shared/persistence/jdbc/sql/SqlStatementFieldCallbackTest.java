package com.alediesme.joyeria.shared.persistence.jdbc.sql;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlStatementFieldCallbackTest {

  private static class SqlHolder {

    @SqlStatement(nameSpace = "customer", value = "getById")
    private String sqlFindById;
  }

  @Test
  void loadsCommonSqlWhenDialectIsCommon() throws Exception {
    SqlHolder holder = new SqlHolder();
    new SqlStatementFieldCallback(holder, SqlDialect.COMMON).doWith(field("sqlFindById"));

    assertTrue(holder.sqlFindById.contains("FROM customer"));
    assertTrue(holder.sqlFindById.contains("WHERE id = :id"));
  }

  @Test
  void oracleDialectFallsBackToCommonWhenNoOracleOverrideExists() throws Exception {
    SqlHolder holder = new SqlHolder();
    new SqlStatementFieldCallback(holder, SqlDialect.ORACLE).doWith(field("sqlFindById"));

    assertTrue(holder.sqlFindById.contains("FROM customer"));
  }

  @Test
  void failsWhenSqlFileDoesNotExist() throws Exception {
    MissingSqlHolder holder = new MissingSqlHolder();
    SqlStatementFieldCallback callback =
        new SqlStatementFieldCallback(holder, SqlDialect.COMMON);

    SqlStatementException exception =
        assertThrows(
            SqlStatementException.class,
            () -> callback.doWith(MissingSqlHolder.class.getDeclaredField("sql")));

    assertTrue(exception.getMessage().contains("SQL file not found"));
  }

  private static class MissingSqlHolder {

    @SqlStatement(nameSpace = "missing", value = "notFound")
    private String sql;
  }

  private Field field(String name) throws NoSuchFieldException {
    Field field = SqlHolder.class.getDeclaredField(name);
    field.setAccessible(true);
    return field;
  }
}
