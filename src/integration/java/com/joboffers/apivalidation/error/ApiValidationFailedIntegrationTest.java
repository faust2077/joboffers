package com.joboffers.apivalidation.error;

import com.fasterxml.jackson.core.type.TypeReference;
import com.joboffers.BaseIntegrationTest;
import com.joboffers.infrastructure.offers.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10")
    );

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_offer_save_request_has_null_and_empty() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "",
                        "position": "",
                        "url": ""
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"));
        // then
        String json = perform.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, new TypeReference<>() {});

        assertThat(result.messages()).containsExactlyInAnyOrder(
                "url must not be empty",
                "companyName must not be empty",
                "position must not be empty",
                "salary must not be null",
                "salary must not be empty"
        );
    }
}
