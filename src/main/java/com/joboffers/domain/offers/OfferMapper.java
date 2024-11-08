package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.HttpResponseOfferDto;
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
                .companyName(offerRequestDto.companyName())
                .position(offerRequestDto.position())
                .salary(offerRequestDto.salary())
                .url(offerRequestDto.url())
                .build();
    }

    static Offer mapFromHttpResponseOfferDtoToOffer(HttpResponseOfferDto httpResponseOfferDto) {
        return Offer.builder()
                .id(httpResponseOfferDto.id())
                .companyName(httpResponseOfferDto.companyName())
                .position(httpResponseOfferDto.position())
                .salary(httpResponseOfferDto.salary())
                .url(httpResponseOfferDto.url())
                .build();
    }

}
