package com.joboffers.infrastructure.offers.scheduler;

import com.joboffers.domain.offers.OffersFacade;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JobOfferScheduler {
    private final OffersFacade offersFacade;
    private static final String STARTED_FETCHING_MESSAGE = "Started fetching offers {}";
    private static final String STOPPED_FETCHING_MESSAGE = "Stopped fetching offers {}";
    private static final String SAVED_NEW_OFFERS_MESSAGE = "Saved {} new offers";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Periodically fetches all offers from the HTTP client, then saves them in a repository.
     * At each scheduled interval this method calls to OffersFacade#fetchAllThenSave, which:
     * 1. Fetches the list of HttpResponseOfferDTOs,
     * 2. Maps to Offer for each element,
     * 3. Filters the elements of list given predicates:
     *      - not null url,
     *      - not empty url,
     *      - not url already existing in the repository,
     * 4. Saves filtered offers in the repository.
     * 5. Returns a list of OfferResponseDto objects.
     * Returned list is used to show information in logging.
     * This method being scheduler's method does not return any values.
     * @see OffersFacade#fetchAllThenSave
     */
    @Scheduled(fixedDelayString = "${joboffers.scheduler.request.delay}")
    public void fetchAllOffersThenSave() {
        log.info(STARTED_FETCHING_MESSAGE, DATE_FORMAT.format(LocalDateTime.now()));
        List<OfferResponseDto> savedOffers = offersFacade.fetchAllThenSave();
        log.info(SAVED_NEW_OFFERS_MESSAGE, savedOffers.size());
        log.info(STOPPED_FETCHING_MESSAGE, DATE_FORMAT.format(LocalDateTime.now()));
    }
}
