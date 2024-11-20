package com.joboffers.infrastructure.security.jwt;

import com.joboffers.infrastructure.security.jwt.dto.JwtResponseDto;
import com.joboffers.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthenticator {
    private final AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateThenGenerateToken(TokenRequestDto tokenRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequestDto.username(), tokenRequestDto.password())
        );

        return JwtResponseDto.builder()
                .build();
    }
}
