package com.joboffers.infrastructure.offers.controller.error;

import org.springframework.http.HttpStatus;

public record OffersPostErrorResponse(String message, HttpStatus status) {
}
