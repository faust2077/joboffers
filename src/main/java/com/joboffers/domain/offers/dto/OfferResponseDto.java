package com.joboffers.domain.offers.dto;

import lombok.Builder;

import java.io.Serializable;

/**
 * Dto representing a job offer fetched from repository.
 * @param id
 * @param companyName
 * @param position
 * @param salary
 * @param url
 */
@Builder
public record OfferResponseDto(
        String id,
        String companyName,
        String position,
        String salary,
        String url
) implements Serializable {}
