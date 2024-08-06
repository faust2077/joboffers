package com.joboffers.domain.offers;

import java.util.List;
import java.util.Optional;

interface OfferRepository
{
    Offer save(Offer offer);

    Optional<Offer> getById(String id);

    boolean existsById(String id);

    boolean existsByUrl(String url);

    List<Offer> getAll();

    List<Offer> saveAll(List<Offer> offers);
}
