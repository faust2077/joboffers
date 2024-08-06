package com.joboffers.domain.offers;

import lombok.Builder;

@Builder
record Offer(String id, String companyName, String position, String salary, String url) {
}
