package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.joboffers.domain.offers.OfferExceptionMessageBuilder.buildOfferAlreadyExistsMessage;
import static com.joboffers.domain.offers.OfferExceptionMessageBuilder.buildOfferNotFoundMessage;

@RequiredArgsConstructor
public class OffersFacade {

    private final OfferRepository repository;

    public List<OfferDto> findAllOffers() {
        return repository.getAll()
                .stream()
                .map(OfferMapper::mapFromOffer)
                .toList();
    }

    public OfferDto findOfferById(String id) {
        return repository.getById(id)
                .map(OfferMapper::mapFromOffer)
                .orElseThrow(() -> new OfferNotFoundException(buildOfferNotFoundMessage(id)));
    }

    public OfferDto saveOffer(OfferDto offerDto) {

        Offer offer = OfferMapper.mapFromOfferDto(offerDto);

        if (repository.existsById(offer.id())) {
            throw new OfferAlreadyExistsException(buildOfferAlreadyExistsMessage(offer));
        }

        Offer savedOffer = repository.save(offer);
        return OfferMapper.mapFromOffer(savedOffer);
    }

}
