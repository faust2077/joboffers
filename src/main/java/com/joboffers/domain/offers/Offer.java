package com.joboffers.domain.offers;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@Document("offers")
record Offer(
        @Id String id,
        String companyName,
        String position,
        String salary,
        @Indexed(unique = true) String url
) {}
