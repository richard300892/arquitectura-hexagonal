package com.alediesme.joyeria.security.port.out;

import com.alediesme.joyeria.security.model.User;
import com.alediesme.joyeria.security.valueobject.Username;

import java.util.Optional;

public interface UserRepository {

  Optional<User> findByUsername(Username username);
}
