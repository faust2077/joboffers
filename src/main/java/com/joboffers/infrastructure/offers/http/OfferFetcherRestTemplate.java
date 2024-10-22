package com.joboffers.infrastructure.offers.http;

import com.joboffers.domain.offers.OfferFetcher;
import com.joboffers.domain.offers.dto.JobOfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetcher {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferDto> remoteFetchAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<HttpHeaders> REQUEST_ENTITY = new HttpEntity<>(headers);

        try {
            final String SERVICE = "/offers";
            String serviceUrl = getServiceUrl(SERVICE);

            final String URL = UriComponentsBuilder.fromHttpUrl(serviceUrl)
                    .toUriString();

            ResponseEntity<List<JobOfferDto>> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    REQUEST_ENTITY,
                    new ParameterizedTypeReference<>() {}
            );

            final List<JobOfferDto> BODY = response.getBody();
            if (BODY == null) {
                log.info("Response body was null. Returning empty list.");
                return Collections.emptyList();
            }
            log.info("Successfully retrieved list of offers.");
            return BODY;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers using http client: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private String getServiceUrl(String service) {
        return uri + ':' + port + service;
    }
}
