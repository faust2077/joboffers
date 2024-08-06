package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;

class OfferMapper {

    static OfferResponseDto mapFromOfferToOfferResponseDto(Offer offer) {
        return OfferResponseDto.builder()
                .id(offer.id())
                .companyName(offer.companyName())
                .position(offer.position())
                .salary(offer.salary())
                .url(offer.url())
                .build();
    }

    static Offer mapFromOfferRequestDtoToOffer(OfferRequestDto offerRequestDto) {
        return Offer.builder()
                .id(offerRequestDto.id())
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .url(offerRequestDto.url())
                .build();
    }

    static Offer mapFromJobOfferDtoToOffer(JobOfferDto jobOfferDto) {
        return Offer.builder()
                .id(jobOfferDto.id())
                .companyName(jobOfferDto.companyName())
                .position(jobOfferDto.position())
                .salary(jobOfferDto.salary())
                .url(jobOfferDto.url())
                .build();
    }

}
