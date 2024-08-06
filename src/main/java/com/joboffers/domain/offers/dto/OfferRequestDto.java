package com.joboffers.domain.offers.dto;

import lombok.Builder;

/**
 * Dto object representing a job offer requested to a repository.
 * @param id
 * @param companyName
 * @param position
 * @param salary
 * @param url
 */
@Builder
public record OfferRequestDto(String id, String companyName, String position, String salary, String url) {
}
