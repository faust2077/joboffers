package com.joboffers.infrastructure.loginandregister.controller;

import com.joboffers.infrastructure.security.jwt.JwtAuthenticator;
import com.joboffers.infrastructure.security.jwt.dto.JwtResponseDto;
import com.joboffers.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TokenRestController {

    private final JwtAuthenticator jwtAuthenticator;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateThenGenerateToken(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        JwtResponseDto jwtResponse = jwtAuthenticator.authenticateThenGenerateToken(tokenRequestDto);
        return ResponseEntity.ok(jwtResponse);
    }

}
