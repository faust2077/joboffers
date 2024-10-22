package com.joboffers.infrastructure.offers.http;

import com.joboffers.domain.offers.OfferFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class OfferFetcherConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler,
                                     @Value("${joboffers.offer-fetcher.http.client.config.connectionTimeout}") Duration connectionTimeout,
                                     @Value("${joboffers.offer-fetcher.http.client.config.readTimeout}") Duration readTimeout) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(connectionTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }

    @Bean
    public OfferFetcher offerFetcher(RestTemplate restTemplate,
                                     @Value("${joboffers.offer-fetcher.http.client.config.uri}") String uri,
                                     @Value("${joboffers.offer-fetcher.http.client.config.port}") int port) {
        return new OfferFetcherRestTemplate(restTemplate, uri, port);
    }

}
