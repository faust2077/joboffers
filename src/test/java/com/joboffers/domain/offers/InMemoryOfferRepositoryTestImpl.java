package com.joboffers.domain.offers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryOfferRepositoryTestImpl implements OfferRepository {

    private final Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer offer) {
        if (existsByUrl(offer.url())) {
            throw new OfferAlreadyExistsException(OfferExceptionMessageBuilder.buildOfferAlreadyExistsMessage(offer));
        }

        if (existsById(offer.id())) {
            throw new DuplicateKeyException(OfferExceptionMessageBuilder.buildDuplicateKeyMessage(offer));
        }

        inMemoryDatabase.put(offer.id(), offer);
        return offer;
    }

    @Override
    public Optional<Offer> getById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Offer> getAll() {
        return inMemoryDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return inMemoryDatabase.values()
                .stream()
                .map(Offer::id)
                .anyMatch(currentId -> currentId.equals(id));
    }

    @Override
    public boolean existsByUrl(String url) {
        return inMemoryDatabase.values()
                .stream()
                .map(Offer::url)
                .anyMatch(currentUrl -> currentUrl.equals(url));
    }


}
