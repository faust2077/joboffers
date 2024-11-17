package com.joboffers.http;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.joboffers.domain.offers.OfferFetcher;
import com.joboffers.SampleJobOfferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class OfferFetcherRestTemplateErrorsIntegrationTest implements SampleJobOfferDto {

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    OfferFetcher offerFetcher = new OfferFetcherRestTemplateConfig().offerFetcher(
            wireMockServer.getPort(),
            Duration.ofSeconds(1),
            Duration.ofSeconds(1)
    );


    @Test
    public void should_throw_response_status_exception_503_service_unavailable_when_fault_connection_reset_by_peer() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));
        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("503 SERVICE_UNAVAILABLE");

    }

    @Test
    public void should_throw_response_status_exception_503_service_unavailable_when_fault_empty_response() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withFault(Fault.EMPTY_RESPONSE)));
        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("503 SERVICE_UNAVAILABLE");

    }

    @Test
    public void should_throw_response_status_exception_503_service_unavailable_when_fault_malformed_response_chunk() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("503 SERVICE_UNAVAILABLE");

    }

    @Test
    public void should_throw_response_status_exception_503_service_unavailable_when_fault_random_data_then_close() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_OK)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("503 SERVICE_UNAVAILABLE");

    }


    @Test
    public void should_throw_response_status_exception_204_no_content_when_status_is_204_no_content() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_NO_CONTENT)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithFourOffersJson())));

        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("204 NO_CONTENT");
    }

    @Test
    public void should_throw_response_status_exception_503_service_unavailable_when_delay_is_5000ms_and_client_has_1000ms_read_timeout() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_NO_CONTENT)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithFourOffersJson())
                        .withFixedDelay(5000)));

        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("503 SERVICE_UNAVAILABLE");
    }

    @Test
    public void should_throw_response_status_exception_401_unauthorized_when_status_is_401_unauthorized() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_UNAUTHORIZED)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithFourOffersJson())));

        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("401 UNAUTHORIZED");
    }

    @Test
    public void should_throw_response_status_exception_404_not_found_when_status_is_404_not_found() {
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(SC_NOT_FOUND)
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithFourOffersJson())));

        // when
        Throwable throwable = catchThrowable(() -> offerFetcher.remoteFetchAll());

        // then
        assertThat(throwable).isInstanceOf(ResponseStatusException.class);
        assertThat(throwable.getMessage()).isEqualTo("404 NOT_FOUND");
    }
}
