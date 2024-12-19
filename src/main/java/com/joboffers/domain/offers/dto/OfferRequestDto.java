package com.joboffers.domain.offers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * Dto object representing a job offer requested to a repository.
 * @param companyName
 * @param position
 * @param salary
 * @param url
 */
@Builder
public record OfferRequestDto(
        @NotNull(message = "{companyName.not.null}")
        @NotEmpty(message = "{companyName.not.empty}")
        String companyName,

        @NotNull(message = "{position.not.null}")
        @NotEmpty(message = "{position.not.empty}")
        String position,

        @NotNull(message = "{salary.not.null}")
        @NotEmpty(message = "{salary.not.empty}")
        String salary,

        @NotNull(message = "{url.not.null}")
        @NotEmpty(message = "{url.not.empty}")
        String url
) {
}
