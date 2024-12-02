package com.joboffers.infrastructure.loginandregister.apivalidation.register;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RegisterValidationErrorDto(List<String> messages, HttpStatus status) {
}
