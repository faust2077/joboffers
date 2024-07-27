package com.joboffers.domain.offers;

class OfferExceptionMessageBuilder {
    static String buildOfferAlreadyExistsMessage(Offer offer) {
        return String.format("Offer with id='%s' already exists", offer.id());
    }

    static String buildOfferNotFoundMessage(String id) {
        return String.format("Offer with id='%s' not found", id);
    }
}
