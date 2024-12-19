package com.joboffers.domain.loginandregister.dto;


import jakarta.validation.constraints.NotBlank;

public record UserRegisterDto(
        @NotBlank(message = "{username.not.blank}")
        String username,
        @NotBlank(message = "{password.not.blank}")
        String password) {}
