package com.joboffers.domain.loginandregister.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDto(
        @NotNull(message = "{username.not.null}")
        @NotEmpty(message = "{username.not.empty}")
        String username,
        @NotNull(message = "{password.not.null}")
        @NotEmpty(message = "{password.not.empty}")
        String password) {}
