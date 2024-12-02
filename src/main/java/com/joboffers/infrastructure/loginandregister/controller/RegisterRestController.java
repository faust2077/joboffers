package com.joboffers.infrastructure.loginandregister.controller;

import com.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import com.joboffers.domain.loginandregister.dto.RegisterResultDto;
import com.joboffers.domain.loginandregister.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterRestController {
    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegisterResultDto> register(@RequestBody UserRegisterDto userRegisterDto) {
        String encodedPassword = passwordEncoder.encode(userRegisterDto.password());
        RegisterResultDto registration = loginAndRegisterFacade.register(
                new UserRegisterDto(userRegisterDto.username(), encodedPassword)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registration);
    }
}
