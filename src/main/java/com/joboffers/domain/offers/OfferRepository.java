package com.joboffers.domain.offers;

import java.util.List;
import java.util.Optional;

public interface OfferRepository
{
    Offer save(Offer offer) throws OfferAlreadyExistsException;

    List<Offer> saveAll(List<Offer> offers);

    Optional<Offer> getById(String id);

    Optional<Offer> getByCompanyName(String companyName);

    Optional<Offer> getByPosition(String position);

    Optional<Offer> getBySalary(String salary);

    Optional<Offer> getByUrl(String url);

    List<Offer> getAll();

    boolean existsById(String id);
}
