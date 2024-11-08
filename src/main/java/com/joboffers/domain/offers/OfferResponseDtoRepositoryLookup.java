package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferResponseDto;

import java.util.List;

public class OfferResponseDtoRepositoryLookup {

    public static List<OfferResponseDto> findAll(OfferRepository repository) {
        return repository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }
}
