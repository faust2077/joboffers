package com.joboffers.infrastructure.security.jwt.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String username, String token) {
}
