package com.joboffers.http;

import com.joboffers.domain.offers.OfferFetcher;
import com.joboffers.infrastructure.offers.http.resttemplate.OfferFetcherConfig;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

class OfferFetcherRestTemplateConfig extends OfferFetcherConfig {
    OfferFetcher offerFetcher(int port, Duration connectionTimeout, Duration readTimeout) {
        RestTemplate restTemplate = restTemplate(
                restTemplateResponseErrorHandler(),
                connectionTimeout,
                readTimeout
        );
        return offerFetcher(restTemplate, "http://localhost", port);
    }
}
