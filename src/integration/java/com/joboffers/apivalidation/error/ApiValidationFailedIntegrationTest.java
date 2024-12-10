package com.joboffers.apivalidation.error;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.infrastructure.loginandregister.apivalidation.login.TokenValidationErrorDto;
import com.joboffers.infrastructure.loginandregister.apivalidation.register.RegisterValidationErrorDto;
import com.joboffers.infrastructure.offers.apivalidation.OfferValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_authorized_offer_save_request_has_null_and_empty() throws Exception {
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
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String json = perform.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferValidationErrorDto result = objectMapper.readValue(json, OfferValidationErrorDto.class);

        assertThat(result.messages()).containsExactlyInAnyOrder(
                "url must not be empty",
                "companyName must not be empty",
                "position must not be empty",
                "salary must not be null",
                "salary must not be empty"
        );
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_user_register_request_has_null_and_empty() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/register")
                .content("""
                        {
                            "username": ""
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String json = perform.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        RegisterValidationErrorDto result = objectMapper.readValue(json, RegisterValidationErrorDto.class);

        assertThat(result.messages()).containsExactlyInAnyOrder(
                "username must not be blank",
                "password must not be blank"
        );
    }

    @Test
    public void should_return_400_bad_request_and_validation_message_when_generate_token_request_has_blank() throws Exception {
        // given
        // when
        ResultActions perform = mockMvc.perform(post("/token")
                .content("""
                        {}
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        String json = perform.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        TokenValidationErrorDto result = objectMapper.readValue(json, TokenValidationErrorDto.class);

        assertThat(result.messages()).containsExactlyInAnyOrder(
                "username must not be blank",
                "password must not be blank"
        );
    }
}
