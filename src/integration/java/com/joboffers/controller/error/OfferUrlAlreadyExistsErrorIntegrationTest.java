package com.joboffers.controller.error;

import com.joboffers.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OfferUrlAlreadyExistsErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10")
    );

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @WithMockUser
    public void should_return_409_conflict_when_user_posted_offer_with_existing_url() throws Exception {
        // step 1
        // given && when
        ResultActions postOfferFirstTime = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "someCompanyName",
                        "position": "somePosition",
                        "salary": "10 000 - 12 000 PLN",
                        "url": "https://offers/offer/231211"
                        }
                        """.trim())
                .contentType(APPLICATION_JSON_VALUE + ";charset=utf-8"));
        // then
        postOfferFirstTime.andExpect(status().isCreated());


        // step 2
        // given && when
        ResultActions postOfferSecondTime = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "someCompanyName",
                        "position": "somePosition",
                        "salary": "10 000 - 12 000 PLN",
                        "url": "https://offers/offer/231211"
                        }
                        """.trim())
                .contentType(APPLICATION_JSON_VALUE + ";charset=utf-8"));
        // then
        postOfferSecondTime.andExpect(status().isConflict());


    }
}
