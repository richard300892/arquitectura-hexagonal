package com.alediesme.joyeria.security.adapter.out.persistence.jdbc;

import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.valueobject.PasswordHash;
import com.alediesme.joyeria.security.valueobject.Role;
import com.alediesme.joyeria.security.valueobject.UserId;
import com.alediesme.joyeria.security.valueobject.Username;
import com.alediesme.joyeria.shared.persistence.jdbc.DatabaseExecution;
import com.alediesme.joyeria.shared.persistence.jdbc.JdbcTemplateProvider;
import com.alediesme.joyeria.shared.persistence.jdbc.sql.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserJdbcQuery {

  private final JdbcTemplateProvider jdbcTemplateProvider;

  @SqlStatement(nameSpace = "security", value = "findByUsername")
  private static String sqlFindByUsername;

  @SqlStatement(nameSpace = "security", value = "findRolesByUserId")
  private static String sqlFindRolesByUserId;

  public UserJdbcQuery(JdbcTemplateProvider jdbcTemplateProvider) {
    this.jdbcTemplateProvider = jdbcTemplateProvider;
  }

  public Optional<User> findByUsername(Username username) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("username", username.value());

    UserRecord record =
        DatabaseExecution.getObjectOrNull(
            () ->
                jdbcTemplateProvider
                    .getTemplate()
                    .queryForObject(sqlFindByUsername, parameters, new UserRecordRowMapper()));

    if (record == null) {
      return Optional.empty();
    }

    Set<Role> roles = new HashSet<>(findRolesByUserId(record.getId()));
    User user =
        new User(
            UserId.of(record.getId()),
            Username.of(record.getUsername()),
            PasswordHash.of(record.getPasswordHash()),
            roles,
            record.isEnabled());

    return Optional.of(user);
  }

  private List<Role> findRolesByUserId(Long userId) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("userId", userId);

    return DatabaseExecution.execute(
        () ->
            jdbcTemplateProvider
                .getTemplate()
                .query(
                    sqlFindRolesByUserId,
                    parameters,
                    (resultSet, rowNum) -> Role.of(resultSet.getString("role_name"))));
  }
}
