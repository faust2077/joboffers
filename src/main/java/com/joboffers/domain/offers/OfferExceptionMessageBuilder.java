package com.joboffers.domain.offers;

class OfferExceptionMessageBuilder {
    static String buildDuplicateKeyMessage(String url) {
        return String.format("Offer with url='%s' already exists", url);
    }

    static String buildOfferNotFoundMessage(String id) {
        return String.format("Offer with id='%s' not found", id);
    }
}
