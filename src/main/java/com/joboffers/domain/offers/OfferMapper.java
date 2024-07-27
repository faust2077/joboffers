package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferDto;

class OfferMapper {
    static OfferDto mapFromOffer(Offer offer) {
                return OfferDto.builder()
                        .id(offer.id())
                        .companyName(offer.companyName())
                        .position(offer.position())
                        .salary(offer.salary())
                        .url(offer.url())
                        .build();
    }

    static Offer mapFromOfferDto(OfferDto offerDto) {
        return Offer.builder()
                .id(offerDto.id())
                .companyName(offerDto.companyName())
                .position(offerDto.position())
                .salary(offerDto.salary())
                .url(offerDto.url())
                .build();
    }
}
