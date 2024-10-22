package com.joboffers.domain.offers;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
@Document("offers")
record Offer(
        @MongoId String id,
        String companyName,
        String position,
        String salary,
        @Indexed(unique = true) String url
) {}
