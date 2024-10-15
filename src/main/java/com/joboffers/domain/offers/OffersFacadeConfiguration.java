package com.joboffers.domain.offers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class OffersFacadeConfiguration {

    @Bean
    OfferRepository offerRepository() {
        return new OfferRepository() {
            @Override
            public Offer save(Offer offer) {
                return null;
            }

            @Override
            public Optional<Offer> getById(String id) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(String id) {
                return false;
            }

            @Override
            public boolean existsByUrl(String url) {
                return false;
            }

            @Override
            public List<Offer> getAll() {
                return Collections.emptyList();
            }

            @Override
            public List<Offer> saveAll(List<Offer> offers) {
                return Collections.emptyList();
            }
        };
    }

    @Bean
    OffersFacade offersFacade(OfferFetcher offerFetcher, OfferRepository offerRepository) {
        OffersFetchAndSaveService offersFetchAndSaveService = new OffersFetchAndSaveService(offerFetcher, offerRepository);
        return new OffersFacade(offersFetchAndSaveService, offerRepository);
    }
}
