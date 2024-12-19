package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.HttpResponseOfferDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;

class TestOnlyOfferMapper {

    static OfferResponseDto mapFromHttpResponseOfferDtoToOfferResponseDto(HttpResponseOfferDto httpResponseOfferDto) {
        return OfferResponseDto.builder()
                .id(httpResponseOfferDto.id())
                .companyName(httpResponseOfferDto.companyName())
                .position(httpResponseOfferDto.position())
                .salary(httpResponseOfferDto.salary())
                .url(httpResponseOfferDto.url())
                .build();
    }
}
