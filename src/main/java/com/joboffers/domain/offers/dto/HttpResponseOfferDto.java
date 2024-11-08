package com.joboffers.domain.offers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public record HttpResponseOfferDto(
        String id,
        @JsonProperty("company") String companyName,
        @JsonProperty("title") String position,
        String salary,
        @JsonProperty("offerUrl") String url
) {
}
