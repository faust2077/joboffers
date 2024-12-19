package com.joboffers.domain.offers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OffersFacadeConfiguration {

    @Bean
    OffersFacade offersFacade(OfferFetcher offerFetcher, OfferRepository offerRepository) {
        OffersFetchAndSaveService offersFetchAndSaveService = new OffersFetchAndSaveService(offerFetcher, offerRepository);
        return new OffersFacade(offersFetchAndSaveService, offerRepository);
    }
}
