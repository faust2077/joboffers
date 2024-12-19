package com.joboffers.domain.loginandregister;

import com.joboffers.domain.loginandregister.dto.RegisterResultDto;
import com.joboffers.domain.loginandregister.dto.UserDto;
import com.joboffers.domain.loginandregister.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import static com.joboffers.domain.loginandregister.LoginAndRegisterExceptionMessageBuilder.buildUserNotFoundMessage;

@RequiredArgsConstructor
public class LoginAndRegisterFacade {
    private final LoginRepository loginRepository;

    public UserDto findByUsername(String username) {
        return loginRepository.findByUsername(username)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new BadCredentialsException(buildUserNotFoundMessage(username)));
    }

    public RegisterResultDto register(UserRegisterDto userRegisterDto) {
        User user = UserMapper.mapFromUserRegisterDtoToUser(userRegisterDto);
        User saved = loginRepository.save(user);
        return UserMapper.mapUserToRegisterResultDto(saved);
    }
}
