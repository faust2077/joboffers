package com.joboffers.domain.offers.dto;

import lombok.Builder;

/**
 * Dto object representing a job offer fetched from external service.
 * @param id
 * @param companyName
 * @param position
 * @param salary
 * @param url
 */
@Builder
public record JobOfferDto(String id, String companyName, String position, String salary, String url) {
}
