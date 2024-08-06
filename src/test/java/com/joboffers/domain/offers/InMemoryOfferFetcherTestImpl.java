package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;

import java.util.List;

class InMemoryOfferFetcherTestImpl implements OfferFetcher {

    private final List<JobOfferDto> jobOffers;

    public InMemoryOfferFetcherTestImpl(List<JobOfferDto> remoteOffers) {
        this.jobOffers = remoteOffers;
    }

    @Override
    public List<JobOfferDto> remoteFetchAll() {
        return jobOffers;
    }
}
