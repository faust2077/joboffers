package com.joboffers.infrastructure.offers.apivalidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record OfferValidationErrorDto(List<String> messages, HttpStatus status) {}
