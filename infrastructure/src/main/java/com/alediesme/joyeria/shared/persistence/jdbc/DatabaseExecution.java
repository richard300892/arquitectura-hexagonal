package com.alediesme.joyeria.shared.persistence.jdbc;

import com.alediesme.joyeria.shared.exception.InfrastructureErrorCode;
import com.alediesme.joyeria.shared.exception.InfrastructureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

public final class DatabaseExecution {

  private DatabaseExecution() {}

  public static <T> T execute(ExecutionCallback<T> callback) {
    try {
      return callback.execute();
    } catch (DataAccessException exception) {
      throw new InfrastructureException(
          InfrastructureErrorCode.DATABASE_ACCESS, "Database operation failed", exception);
    }
  }

  public static <T> T getObjectOrNull(ExecutionCallback<T> callback) {
    try {
      return callback.execute();
    } catch (EmptyResultDataAccessException exception) {
      return null;
    } catch (DataAccessException exception) {
      throw new InfrastructureException(
          InfrastructureErrorCode.DATABASE_ACCESS, "Database operation failed", exception);
    }
  }
}
