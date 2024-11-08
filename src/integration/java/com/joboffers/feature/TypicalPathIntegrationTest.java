package com.joboffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.BaseIntegrationTest;
import com.joboffers.domain.offers.OfferRepository;
import com.joboffers.domain.offers.OfferResponseDtoRepositoryLookup;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import com.joboffers.infrastructure.offers.scheduler.JobOfferScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalPathIntegrationTest extends BaseIntegrationTest implements SampleJobOfferDto {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("joboffers.offer-fetcher.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("joboffers.offer-fetcher.http.client.config.port", () -> wireMockServer.getPort());
    }

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private JobOfferScheduler offersFetchingScheduler;

    @Test
    public void user_wants_to_see_offers_but_has_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {

        final String offersEndpoint = "/offers";

//step 1: there are no offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)
        // given && when && then
        wireMockServer.stubFor(WireMock.get(offersEndpoint)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithZeroOffersJson())
                )
        );


//step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given && when
        offersFetchingScheduler.fetchAllOffersThenSave();
        // then
        assertThat(OfferResponseDtoRepositoryLookup.findAll(offerRepository)).isEmpty();


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
        // given && when
        ResultActions performGetOffersWhenOffersAbsent = mockMvc.perform(get(offersEndpoint)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String jsonWithOffers = performGetOffersWhenOffersAbsent.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var noOffers = objectMapper.readValue(jsonWithOffers, new TypeReference<List<OfferResponseDto>>() {
        });

        assertThat(noOffers).isNotNull();
        assertThat(noOffers).isEmpty();


//step 8: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get(offersEndpoint)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithTwoOffersJson())
                )
        );


//step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with some ids generated by db: id1 and id2 (where id1 != id2) to database
        // given && when
        offersFetchingScheduler.fetchAllOffersThenSave();
        // then
        List<OfferResponseDto> allOffersInRepository = OfferResponseDtoRepositoryLookup.findAll(offerRepository);
        assertThat(allOffersInRepository).hasSize(2);


//step 10: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: id1 and id2
        // given && when
        ResultActions performGetTwoOffersByIds = mockMvc.perform(get(offersEndpoint)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String jsonWithTwoOffers = performGetTwoOffersByIds.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var twoOffers = objectMapper.readValue(jsonWithTwoOffers, new TypeReference<List<OfferResponseDto>>() {
        });

        assertThat(twoOffers).isNotNull();
        assertThat(twoOffers).hasSize(2);


        List<OfferResponseDto> twoOffersInRepositoryList = OfferResponseDtoRepositoryLookup.findAll(offerRepository);
        OfferResponseDto expectedFirstOffer = twoOffersInRepositoryList.get(0);
        OfferResponseDto expectedSecondOffer = twoOffersInRepositoryList.get(1);

        assertThat(twoOffers).containsExactlyInAnyOrder(
                new OfferResponseDto(expectedFirstOffer.id(),
                        expectedFirstOffer.companyName(),
                        expectedFirstOffer.position(),
                        expectedFirstOffer.salary(),
                        expectedFirstOffer.url()),
                new OfferResponseDto(expectedSecondOffer.id(),
                        expectedSecondOffer.companyName(),
                        expectedSecondOffer.position(),
                        expectedSecondOffer.salary(),
                        expectedSecondOffer.url())
        );


// step 11: user made GET /offers/11000 and system returned NOT_FOUND(404) with message “Offer with id 11000 not found”
        // given
        final String offers11000 = offersEndpoint + "/11000";
        // when
        ResultActions performGetOffersWithNotExistingId = mockMvc.perform(get(offers11000));
        // then
        performGetOffersWithNotExistingId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message": "Offer with id='11000' not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


//step 12: user made GET /offers/id1 and system returned OK(200) with offer
        // given
        final String id1 = expectedFirstOffer.id();
        final String offersId1Endpoint = offersEndpoint + '/' + id1;
        // when
        ResultActions performGetOffersById1 = mockMvc.perform(get(offersId1Endpoint)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String jsonWithOneOffer = performGetOffersById1.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferResponseDto oneOffer = objectMapper.readValue(jsonWithOneOffer, OfferResponseDto.class);

        assertThat(oneOffer).isNotNull();
        assertThat(oneOffer).isEqualTo(expectedFirstOffer);


//step 13: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get(offersEndpoint)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(bodyWithFourOffersJson())
                )
        );


//step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with some ids generated by db: id3 and id4 (where id3 != id4) to database
        // given && when
        offersFetchingScheduler.fetchAllOffersThenSave();
        // then
        assertThat(OfferResponseDtoRepositoryLookup.findAll(offerRepository)).hasSize(4);


//step 15: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: id1, id2, id3, id4
        // given && when
        ResultActions performGetOffers = mockMvc.perform(get(offersEndpoint)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String jsonWithFourOffers = performGetOffers.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var fourOffers = objectMapper.readValue(jsonWithFourOffers, new TypeReference<List<OfferResponseDto>>() {
        });

        assertThat(fourOffers).isNotNull();
        assertThat(fourOffers).hasSize(4);

        List<OfferResponseDto> fourOffersInRepositoryList = OfferResponseDtoRepositoryLookup.findAll(offerRepository);

        final OfferResponseDto expectedOffer1 = fourOffersInRepositoryList.stream()
                .filter(offer -> offer.url()
                        .equals("https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"))
                .findAny()
                .orElseThrow(() -> new AssertionError("Expected offer 1 not found"));

        final OfferResponseDto expectedOffer2 = fourOffersInRepositoryList.stream()
                .filter(offer -> offer.url()
                        .equals("https://nofluffjobs.com/pl/job/junior-full-stack-developer-vertabelo-remote-k7m9xpnm"))
                .findAny()
                .orElseThrow(() -> new AssertionError("Expected offer 2 not found"));

        assertThat(fourOffers).contains(
                new OfferResponseDto(expectedOffer1.id(),
                        expectedOffer1.companyName(),
                        expectedOffer1.position(),
                        expectedOffer1.salary(),
                        expectedOffer1.url()),
                new OfferResponseDto(expectedOffer2.id(),
                        expectedOffer2.companyName(),
                        expectedOffer2.position(),
                        expectedOffer2.salary(),
                        expectedOffer2.url())
        );

//step 16: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned CREATED(201) with saved offer
        // given && when
        ResultActions performPostOffers = mockMvc.perform(post(offersEndpoint)
                .content("""
                        {
                        "companyName": "someCompanyName",
                        "position": "somePosition",
                        "salary": "17 000 - 21 000 PLN",
                        "url": "https://offers/offer/13412"
                        }
                        """.trim())
                .contentType(APPLICATION_JSON_VALUE + ";charset=utf-8")
        );
        // then
        String createdOfferJson = performPostOffers.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferResponseDto createdOfferObject = objectMapper.readValue(createdOfferJson, OfferResponseDto.class);

        String id = createdOfferObject.id();
        assertAll(
                () -> assertThat(id).isNotNull(),
                () -> assertThat(createdOfferObject.url()).isEqualTo("https://offers/offer/13412"),
                () -> assertThat(createdOfferObject.companyName()).isEqualTo("someCompanyName"),
                () -> assertThat(createdOfferObject.position()).isEqualTo("somePosition"),
                () -> assertThat(createdOfferObject.salary()).isEqualTo("17 000 - 21 000 PLN")
        );

//step 17: user made GET /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 1 offer
        // given && when
        ResultActions performGetOffersAfterPost = mockMvc.perform(get(offersEndpoint)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String oneOfferJson = performGetOffersAfterPost.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<OfferResponseDto> oneOfferParsedJson = objectMapper.readValue(oneOfferJson, new TypeReference<>() {
        });

        assertThat(oneOfferParsedJson).hasSize(5);
        assertThat(oneOfferParsedJson.stream()
                .map(OfferResponseDto::id)).contains(id);
    }
}
