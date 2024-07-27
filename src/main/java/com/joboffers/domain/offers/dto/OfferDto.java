package com.joboffers.domain.offers.dto;

import lombok.Builder;

@Builder
public record OfferDto(String id, String companyName, String position, String salary, String url) {}
