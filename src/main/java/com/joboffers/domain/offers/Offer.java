package com.joboffers.domain.offers;

import lombok.Builder;

// entity object
@Builder
record Offer(String id, String companyName, String position, String salary, String url) {
}
