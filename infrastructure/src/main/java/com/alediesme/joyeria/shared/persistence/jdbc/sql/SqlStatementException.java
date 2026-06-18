package com.alediesme.joyeria.shared.persistence.jdbc.sql;

import com.alediesme.joyeria.shared.exception.InfrastructureErrorCode;
import com.alediesme.joyeria.shared.exception.InfrastructureException;

public class SqlStatementException extends InfrastructureException {

  public SqlStatementException(String message) {
    super(InfrastructureErrorCode.SQL_STATEMENT, message);
  }
}
