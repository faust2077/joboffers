package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import static com.joboffers.domain.offers.OfferExceptionMessageBuilder.buildOfferNotFoundMessage;

@RequiredArgsConstructor
public class OffersFacade {

    private final OffersFetchAndSaveService offersFetchAndSaveService;
    private final OfferRepository repository;

    @Cacheable(value = "jobOffers")
    public List<OfferResponseDto> findAllOffers() {
        return repository.findAll()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

    public OfferResponseDto findOfferById(String id) {
        return repository.findById(id)
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .orElseThrow(() -> new OfferNotFoundException(buildOfferNotFoundMessage(id)));
    }

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offer = OfferMapper.mapFromOfferRequestDtoToOffer(offerRequestDto);
        Offer savedOffer = repository.save(offer);
        return OfferMapper.mapFromOfferToOfferResponseDto(savedOffer);
    }

    public List<OfferResponseDto> fetchAllThenSave() {
        return offersFetchAndSaveService.fetchAllThenSave()
                .stream()
                .map(OfferMapper::mapFromOfferToOfferResponseDto)
                .toList();
    }

}
