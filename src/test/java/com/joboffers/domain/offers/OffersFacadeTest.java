package com.joboffers.domain.offers;

import org.junit.jupiter.api.Test;

class OffersFacadeTest {
    @Test
    void whenAllOffersFound_thenSuccess() {}

    @Test
    void whenNoneOffersFoundInNonEmptyRepository_thenFail() {}

    @Test
    void whenNotEachSingleOfferFound_thenFail() {}

    @Test
    void whenUnauthorizedRequestingAllOffers_thenFail() {}

    @Test
    void whenAuthorizedRequestingAllOffers_thenSuccess() {}

    @Test
    void whenOfferWithGivenIdFound_thenSuccess() {}

    @Test
    void whenGivenBadOfferIdFormat_thenFail() {}

    @Test
    void whenNotUniqueOfferIdInRepository_thenFail() {}

    @Test
    void whenOfferWithGivenIdNotInRepository_thenFail() {}

    @Test
    void whenOfferWithGivenIdInRepositoryNotFound_thenFail() {}

    @Test
    void whenUnauthorizedRequestingOfferById_thenFail() {}

    @Test
    void whenAuthorizedRequestingOfferById_thenSuccess() {}

    @Test
    void whenGivenBadOfferFormat_thenFail() {}

    @Test
    void whenGivenCorrectOfferFormat_thenSuccess() {}

    @Test
    void whenSavingOfferThatAlreadyExistsInRepository_thenFail() {}

    @Test
    void whenSavingOfferAndRepositoryIsFull_thenFail() {}

    @Test
    void whenUnauthorizedSavingOffer_thenFail() {}

    @Test
    void whenAuthorizedSavingOffer_thenSuccess() {}

}