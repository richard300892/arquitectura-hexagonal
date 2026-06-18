package com.alediesme.joyeria.security.adapter.out.persistence.jdbc;

import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.port.out.UserRepository;
import com.alediesme.joyeria.security.valueobject.Username;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

  private final UserJdbcQuery userJdbcQuery;

  public JdbcUserRepository(UserJdbcQuery userJdbcQuery) {
    this.userJdbcQuery = userJdbcQuery;
  }

  @Override
  public Optional<User> findByUsername(Username username) {
    return userJdbcQuery.findByUsername(username);
  }
}
