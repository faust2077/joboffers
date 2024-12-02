package com.joboffers.infrastructure.loginandregister.apivalidation.login;

import org.springframework.http.HttpStatus;

import java.util.List;

public record TokenValidationErrorDto(List<String> messages, HttpStatus status) {
}
