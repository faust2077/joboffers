package com.joboffers.feature;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.BaseIntegrationTest;
import com.joboffers.domain.offers.OfferFetcher;
import com.joboffers.domain.offers.dto.JobOfferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TypicalPathIntegrationTest extends BaseIntegrationTest implements SampleJobOfferDto {

    @Autowired
    OfferFetcher offerFetcher;

    @Test
    public void user_wants_to_see_offers_but_has_to_be_logged_in_and_external_server_should_have_some_offers() {


//step 1: there are no offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", "application/json")
                                .withBody(bodyWithZeroOffersJson())
                        )
        );
        // when
        List<JobOfferDto> jobOfferDtoList = offerFetcher.remoteFetchAll();
        // then

//step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given
        // when
        // then
//step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // given
        // when
        // then
//step 4: user made GET /offers with no jwt token and system returned UNAUTHORIZED
        // given
        // when
        // then
//step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status OK(200)
        // given
        // when
        // then
//step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given
        // when
        // then
//step 7: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        // given
        // when
        // then
//step 8: there are 2 new offers in external HTTP server
        // given
        // when
        // then
//step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        // given
        // when
        // then
//step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // given
        // when
        // then
// step 11: user made GET /offers/9999 and system returned NOT_FOUND(404) with message “Offer with id 9999 not found”
        // given
        // when
        // then
//step 12: user made GET /offers/1000 and system returned OK(200) with offer
        // given
        // when
        // then
//step 13: there are 2 new offers in external HTTP server
        // given
        // when
        // then
//step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        // given
        // when
        // then
//step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        // given
        // when
        // then
//step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned CREATED(201) with saved offer
        // given
        // when
        // then
//step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer
        // given
        // when
        // then
    }
}
