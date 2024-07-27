package com.joboffers.domain.offers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryOfferRepositoryTestImpl implements OfferRepository {

    private final Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Offer save(Offer offer) {
        inMemoryDatabase.put(offer.id(), offer);
        return offer;
    }

    @Override
    public List<Offer> saveAll(List<Offer> offers) {
        return offers.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public Optional<Offer> getById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public Optional<Offer> getByCompanyName(String companyName) {
        return Optional.ofNullable(inMemoryDatabase.get(companyName));
    }

    @Override
    public Optional<Offer> getByPosition(String position) {
        return Optional.ofNullable(inMemoryDatabase.get(position));
    }

    @Override
    public Optional<Offer> getBySalary(String salary) {
        return Optional.ofNullable(inMemoryDatabase.get(salary));
    }

    @Override
    public Optional<Offer> getByUrl(String url) {
        return Optional.ofNullable(inMemoryDatabase.get(url));
    }

    @Override
    public List<Offer> getAll() {
        return inMemoryDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return inMemoryDatabase.containsKey(id);
    }

}
