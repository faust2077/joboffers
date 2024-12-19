package com.joboffers.infrastructure.security.jwt;

import com.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.joboffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    private final LoginAndRegisterFacade loginAndRegisterFacade;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto byUsername = loginAndRegisterFacade.findByUsername(username);
        return getAsUserDetails(byUsername);
    }


    private UserDetails getAsUserDetails(UserDto userDto) {
        return new org.springframework.security.core.userdetails.User(
                userDto.username(),
                userDto.password(),
                Collections.emptyList()
        );
    }

}
