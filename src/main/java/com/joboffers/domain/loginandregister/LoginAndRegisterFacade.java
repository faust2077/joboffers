package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.RegisterResultDto;
import com.joboffers.domain.loginandregister.dto.UserDto;
import com.joboffers.domain.loginandregister.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;

import static com.joboffers.domain.loginandregister.LoginAndRegisterExceptionMessageBuilder.buildUserNotFoundMessage;

@RequiredArgsConstructor
public class LoginAndRegisterFacade {
    private final LoginRepository repository;

    public UserDto findByUsername(String username) {
        return repository.getByUsername(username)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(buildUserNotFoundMessage(username)));
    }

    public RegisterResultDto register(UserRegisterDto userRegisterDto) {
        User user = UserMapper.mapFromUserRegisterDtoToUser(userRegisterDto);
        User saved = repository.save(user);
        return UserMapper.mapUserToRegisterResultDto(saved);
    }
}
