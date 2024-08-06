package com.joboffers.domain.loginandregister;

class LoginAndRegisterExceptionMessageBuilder {
    static String buildUserNotFoundMessage(String username) {
        return String.format("User with the username: %s has no account linked", username);
    }

    static String buildUserAlreadyExistsMessage(String username) {
        return String.format("User with the username: %s already has a linked account", username);
    }
}
