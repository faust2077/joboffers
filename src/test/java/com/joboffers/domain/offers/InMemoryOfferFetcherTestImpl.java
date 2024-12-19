package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.HttpResponseOfferDto;

import java.util.List;

class InMemoryOfferFetcherTestImpl implements OfferFetcher {

    private final List<HttpResponseOfferDto> jobOffers;

    public InMemoryOfferFetcherTestImpl(List<HttpResponseOfferDto> remoteOffers) {
        this.jobOffers = remoteOffers;
    }

    @Override
    public List<HttpResponseOfferDto> remoteFetchAll() {
        return jobOffers;
    }
}
