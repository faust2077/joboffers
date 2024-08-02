package com.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.joboffers.domain.loginandregister.LoginAndRegisterExceptionMessageBuilder.buildUserAlreadyExistsMessage;

class InMemoryLoginRepositoryImpl implements LoginRepository {

    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    @Override
    public User save(User user) {

        final String ID = generateID();

        User savedUser = new User(
                ID,
                user.username(),
                user.password()
        );

        if (existsByUsername(savedUser)) {
            throw new UserAlreadyExistsException(buildUserAlreadyExistsMessage(user.username()));
        }

        usersByUsername.put(savedUser.username(), savedUser);
        return savedUser;
    }

    private static String generateID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean existsByUsername(User user) {
        return usersByUsername.values()
                .stream()
                .anyMatch(currUser -> currUser.username().equals(user.username()));
    }
}
