package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;

class TestOnlyOfferMapper {

    static OfferRequestDto mapFromOfferToOfferRequestDto(Offer offer) {
        return OfferRequestDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .url(offer.url())
                .build();
    }

    static OfferResponseDto mapFromJobOfferDtoToOfferResponseDto(JobOfferDto jobOfferDto) {
        return OfferResponseDto.builder()
                .id(jobOfferDto.id())
                .companyName(jobOfferDto.companyName())
                .position(jobOfferDto.position())
                .salary(jobOfferDto.salary())
                .url(jobOfferDto.url())
                .build();
    }
}
