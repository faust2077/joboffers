package com.joboffers.infrastructure.security.jwt.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties("auth.jwt")
@EnableConfigurationProperties()
public record JwtConfigurationProperties(
        String secret,
        int expirationDays,
        String issuer
) {

}
