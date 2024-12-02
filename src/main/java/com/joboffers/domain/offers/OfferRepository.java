package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferResponseDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String>
{
    boolean existsByUrl(String url);

    default List<OfferResponseDto> findAllMappedToOfferResponseDto() {
        return this.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }
}
