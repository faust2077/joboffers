package com.joboffers.domain.offers;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
class OffersFetchAndSaveService {

    private final OfferFetcher offerFetcher;
    private final OfferRepository offerRepository;

    List<Offer> fetchAllThenSave() {
        List<Offer> offers = fetchAll()
                .filter(this::isNotExistingOffer)
                .toList();
        return offerRepository.saveAll(offers);
    }

    private Stream<Offer> fetchAll() {
        return offerFetcher.remoteFetchAll()
                .stream()
                .map(OfferMapper::mapFromJobOfferDtoToOffer);
    }

    private boolean isNotExistingOffer(Offer offer) {
        return !offer.url().isEmpty() && !offerRepository.existsByUrl(offer.url());
    }

}
