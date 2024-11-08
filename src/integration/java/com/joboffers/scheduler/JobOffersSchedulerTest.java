package com.joboffers.scheduler;

import com.joboffers.BaseIntegrationTest;
import com.joboffers.JobOffersApplication;
import com.joboffers.domain.offers.OfferFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = JobOffersApplication.class, properties = "scheduling.enabled=true")
public class JobOffersSchedulerTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @SpyBean
    private OfferFetcher offerFetcher;

    @Test
    public void should_run_http_client_offers_fetching_exactly_specified_times() {
        Duration maximumTimeout = Duration.ofSeconds(3);
        int expectedNumberOfInvocations = 2;

        await().atMost(maximumTimeout)
                .untilAsserted(() -> verify(offerFetcher, times(expectedNumberOfInvocations)).remoteFetchAll());
    }
}
