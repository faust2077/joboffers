package com.joboffers.cache;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.domain.offers.OffersFacade;
import com.joboffers.infrastructure.security.jwt.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RedisCacheIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> redis;

    static {
        redis = new GenericContainer<>("redis").withExposedPorts(6379);
        redis.start();
    }

    @SpyBean
    private OffersFacade offersFacade;

    @Autowired
    private CacheManager cacheManager;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", () -> redis.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    void should_save_offers_to_cache_then_invalidate_by_time_to_live() throws Exception {
        // step 1: user registered with someUser and somePassword
        // given && when
        ResultActions performPostRegister = mockMvc.perform(post("/register")
                .content("""
                         {
                            "username": "someUser",
                            "password": "somePassword"
                         }
                         """)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        performPostRegister.andExpect(status().isCreated());


        // step 2: user logged in with someUser and somePassword
        // given && when
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                            "username": "someUser",
                            "password": "somePassword"
                        }
                        """.trim())
                .contentType(APPLICATION_JSON_VALUE));
        // then
        String jwtResponseJson = successLoginRequest.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponseDto jwtResponseDto = objectMapper.readValue(jwtResponseJson, JwtResponseDto.class);

        final String token = jwtResponseDto.token();

        // step 3: redis cache saved GET /offers request
        // given && when
        mockMvc.perform(get("/offers")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON_VALUE));
        // then
        verify(offersFacade, times(1)).findAllOffers();
        assertThat(cacheManager.getCacheNames().contains("jobOffers")).isTrue();


        // step 4: redis cache invalidated caches by time to live
        await().atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                    mockMvc.perform(get("/offers")
                            .header("Authorization", "Bearer " + token)
                            .contentType(APPLICATION_JSON_VALUE));
                    verify(offersFacade, atLeast(2)).findAllOffers();
                });
    }
}
