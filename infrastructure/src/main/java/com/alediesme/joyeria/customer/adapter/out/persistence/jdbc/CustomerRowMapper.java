package com.alediesme.joyeria.customer.adapter.out.persistence.jdbc;

import com.alediesme.joyeria.customer.model.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerRowMapper implements RowMapper<Customer> {

  @Override
  public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return Customer.restore(
        resultSet.getLong("id"),
        getNullableLong(resultSet, "app_user_id"),
        resultSet.getLong("document_type_id"),
        resultSet.getString("document_number"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("email"),
        resultSet.getString("phone"),
        resultSet.getString("mobile_phone"),
        getNullableLong(resultSet, "gender_id"),
        getNullableLong(resultSet, "marital_status_id"),
        toLocalDate(resultSet.getDate("birth_date")),
        toLocalDate(resultSet.getDate("anniversary_date")),
        getNullableLong(resultSet, "neighborhood_id"),
        resultSet.getString("address_line"),
        resultSet.getString("address_complement"),
        resultSet.getString("postal_code"),
        getNullableLong(resultSet, "preferred_currency_id"),
        resultSet.getInt("accepts_marketing") == 1,
        resultSet.getString("notes"),
        resultSet.getInt("enabled") == 1);
  }

  private Long getNullableLong(ResultSet resultSet, String column) throws SQLException {
    long value = resultSet.getLong(column);
    return resultSet.wasNull() ? null : value;
  }

  private LocalDate toLocalDate(Date date) {
    if (date == null) {
      return null;
    }
    return date.toLocalDate();
  }
}
