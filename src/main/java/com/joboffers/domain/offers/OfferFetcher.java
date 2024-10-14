package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;

import java.util.List;

public interface OfferFetcher {
    List<JobOfferDto> remoteFetchAll();
}
