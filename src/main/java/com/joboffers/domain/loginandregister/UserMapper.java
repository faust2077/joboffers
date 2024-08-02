package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.RegisterResultDto;
import com.joboffers.domain.loginandregister.dto.UserDto;
import com.joboffers.domain.loginandregister.dto.UserRegisterDto;

class UserMapper {
    static UserDto mapFromUserToUserDto(User user) {
        return new UserDto(user.id(), user.username(), user.password());
    }

    static UserRegisterDto mapFromUserToUserRegisterDto(User user) {
        return new UserRegisterDto(user.username(), user.password());
    }

    static User mapFromUserRegisterDtoToUser(UserRegisterDto userRegisterDto) {
        return new User(null, userRegisterDto.username(), userRegisterDto.password());
    }

    static RegisterResultDto mapUserToRegisterResultDto(User saved) {
        return new RegisterResultDto(saved.id(), true, saved.username());
    }
}
