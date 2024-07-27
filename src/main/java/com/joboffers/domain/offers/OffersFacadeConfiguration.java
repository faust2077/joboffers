package com.joboffers.domain.offers;

public class OffersFacadeConfiguration {
    OffersFacade createForTest(OfferRepository repository) {
        return new OffersFacade(repository);
    }
}
