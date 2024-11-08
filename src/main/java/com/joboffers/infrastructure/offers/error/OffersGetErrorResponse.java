package com.joboffers.infrastructure.offers.error;

import org.springframework.http.HttpStatus;

public record OffersGetErrorResponse(String message, HttpStatus status) {}
