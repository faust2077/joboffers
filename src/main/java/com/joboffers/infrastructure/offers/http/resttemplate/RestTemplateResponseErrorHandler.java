package com.joboffers.infrastructure.offers.http.resttemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final String HTTP_REQUEST_FAILURE_MESSAGE = "Server encountered an error while making an HTTP request.";

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        final HttpStatusCode STATUS_CODE = httpResponse.getStatusCode();

        if (STATUS_CODE.is5xxServerError()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, HTTP_REQUEST_FAILURE_MESSAGE);
        } else if (STATUS_CODE.is4xxClientError()) {
            switch (resolveHttpStatus(STATUS_CODE)) {
                case NOT_FOUND -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                case UNAUTHORIZED -> throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    private static HttpStatus resolveHttpStatus(HttpStatusCode STATUS_CODE) {
        return Objects.requireNonNull(
                HttpStatus.resolve(STATUS_CODE.value())
        );
    }
}
