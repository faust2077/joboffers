package com.joboffers.infrastructure.offers.scheduler;

import com.joboffers.domain.offers.OffersFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class JobOfferScheduler {
    private final OffersFacade offersFacade;

    // na crontab jest 5, a tu 6 wartosci
    // scheduler odpala swoje wlasne watki
    @Scheduled(cron = "*/3 * * * * *")
    public void fetchAllOffersThenSave() {
        log.info("Job offer scheduler started");
//        offersFacade.fetchAllThenSave();
    }
}
