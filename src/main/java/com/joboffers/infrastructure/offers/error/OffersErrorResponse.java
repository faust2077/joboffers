package com.joboffers.infrastructure.offers.error;

import org.springframework.http.HttpStatus;

public record OffersErrorResponse(String message, HttpStatus status) {}
