package com.joboffers.domain.loginandregister;

import java.util.Optional;

interface LoginRepository {

    Optional<User> getByUsername(String username);

    User save(User user);

    boolean existsByUsername(User user);
}
