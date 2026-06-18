package com.alediesme.joyeria.security.adapter.out.persistence.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRecordRowMapper implements RowMapper<UserRecord> {

  @Override
  public UserRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new UserRecord(
        resultSet.getLong("id"),
        resultSet.getString("username"),
        resultSet.getString("password_hash"),
        resultSet.getInt("enabled") == 1);
  }
}
