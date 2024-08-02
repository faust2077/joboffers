package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.RegisterResultDto;
import com.joboffers.domain.loginandregister.dto.UserDto;
import com.joboffers.domain.loginandregister.dto.UserRegisterDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    private final InMemoryLoginRepositoryImpl testRepository = new InMemoryLoginRepositoryImpl();
    private final LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(testRepository);

    @Test
    void testFindByUsername_whenUserFound_thenSuccess() {

        // given
        final String USERNAME = "username";
        UserRegisterDto userRegisterDto = new UserRegisterDto(USERNAME, "password");
        RegisterResultDto registration = loginAndRegisterFacade.register(userRegisterDto);

        // when
        UserDto actualUserDto = loginAndRegisterFacade.findByUsername(USERNAME);

        // then
        UserDto expectedUserDto = new UserDto(registration.id(), registration.username(), userRegisterDto.password());
        assertThat(actualUserDto).isEqualTo(expectedUserDto);

    }

    @Test
    void testFindByUsername_whenUserNotFound_thenThrowsUserNotFoundException() {

        // given
        final String USERNAME = "username";

        // when
        Throwable thrown = catchThrowable(() -> loginAndRegisterFacade.findByUsername(USERNAME));

        // then
        final String USER_NOT_FOUND = LoginAndRegisterExceptionMessageBuilder.buildUserNotFoundMessage(USERNAME);

        assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(USER_NOT_FOUND);
    }

    @Test
    void testRegistering_whenNewUserRegistered_thenSuccess() {

        // given
        final String USERNAME = "username";
        final UserRegisterDto userRegisterDto = new UserRegisterDto(USERNAME, "password");

        // when
        RegisterResultDto actualRegistration = loginAndRegisterFacade.register(userRegisterDto);

        // then
        UserDto actualUserByUsername = loginAndRegisterFacade.findByUsername(USERNAME);
        RegisterResultDto expectedRegistration = new RegisterResultDto(actualUserByUsername.id(), true, actualUserByUsername.username());

        assertThat(actualUserByUsername.username()).isEqualTo(expectedRegistration.username());
        assertThat(actualRegistration).isEqualTo(expectedRegistration);
    }

    @Test
    void testRegistering_whenOldUserTryingToRegister_thenThrowsUserAlreadyExistsException() {

        // given
        final UserRegisterDto USER = UserMapper.mapFromUserToUserRegisterDto(
                new User("id", "username", "password")
        );

        // when
        loginAndRegisterFacade.register(USER);
        Throwable thrown = catchThrowable(
                () -> loginAndRegisterFacade.register(USER)
        );

        // then
        final String USER_ALREADY_EXISTS = LoginAndRegisterExceptionMessageBuilder.buildUserAlreadyExistsMessage(
                USER.username()
        );

        assertThat(thrown)
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(USER_ALREADY_EXISTS);
    }

}