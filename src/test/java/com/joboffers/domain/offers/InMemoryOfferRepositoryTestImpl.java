package com.joboffers.domain.offers;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.StreamSupport;

class InMemoryOfferRepositoryTestImpl implements OfferRepository {

    private final Map<String, Offer> inMemoryDatabase = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public <S extends Offer> S save(S entity) {

        final String url = entity.url();

        if (existsByUrl(url)) {
            throw new DuplicateKeyException(OfferExceptionMessageBuilder.buildDuplicateKeyMessage(url));
        }

        final String ID = generateID();

        Offer savedOffer = new Offer(
                ID,
                entity.companyName(),
                entity.position(),
                entity.salary(),
                url
        );

        inMemoryDatabase.put(savedOffer.id(), savedOffer);

        return (S) savedOffer;
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<Offer> findById(String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Offer> findAll() {
        return inMemoryDatabase.values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return inMemoryDatabase.containsKey(id);
    }

    @Override
    public boolean existsByUrl(String url) {
        return inMemoryDatabase.values()
                .stream()
                .map(Offer::url)
                .anyMatch(currentUrl -> currentUrl.equals(url));
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }
}
