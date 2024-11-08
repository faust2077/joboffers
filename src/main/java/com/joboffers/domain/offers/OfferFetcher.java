package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.HttpResponseOfferDto;

import java.util.List;

public interface OfferFetcher {
    List<HttpResponseOfferDto> remoteFetchAll();
}
