package com.joboffers.infrastructure.offers.http.resttemplate;

import com.joboffers.domain.offers.OfferFetcher;
import com.joboffers.domain.offers.dto.HttpResponseOfferDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferFetcherRestTemplate implements OfferFetcher {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<HttpResponseOfferDto> remoteFetchAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<HttpHeaders> REQUEST_ENTITY = new HttpEntity<>(headers);

        try {
            String serviceUrl = getServiceUrl();

            final String URL = UriComponentsBuilder.fromHttpUrl(serviceUrl)
                    .toUriString();

            ResponseEntity<List<HttpResponseOfferDto>> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    REQUEST_ENTITY,
                    new ParameterizedTypeReference<>() {}
            );

            final List<HttpResponseOfferDto> BODY = response.getBody();
            if (BODY == null) {
                log.error("Response body was null. Returning empty list.");
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            log.info("Successfully retrieved list of offers.");
            return BODY;
        } catch (ResourceAccessException e) {
            log.error("Connection error while fetching offers using http client: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        } catch (HttpClientErrorException e) {
            log.error("HTTP Client-side error while fetching offers using http client: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        } catch (HttpServerErrorException e) {
            log.error("HTTP Server-side error while fetching offers using http client: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        } catch (RestClientException e) {
            log.error("Unexpected error while fetching offers using http client: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getServiceUrl() {
        return uri + ':' + port + "/offers";
    }
}
