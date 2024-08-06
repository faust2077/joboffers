package com.joboffers.domain.offers;

class OfferExceptionMessageBuilder {
    static String buildDuplicateKeyMessage(Offer offer) {
        return String.format("Given key: id='%s' is already linked with another offer in the database", offer.id());
    }

    static String buildOfferAlreadyExistsMessage(Offer offer) {
        return String.format("Offer with url='%s' already exists", offer.url());
    }

    static String buildOfferNotFoundMessage(String id) {
        return String.format("Offer with id='%s' not found", id);
    }
}
