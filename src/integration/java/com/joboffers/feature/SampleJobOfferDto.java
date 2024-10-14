package com.joboffers.feature;

public interface SampleJobOfferDto {
    default String bodyWithZeroOffersJson() {
        return "[]";
    }
}
