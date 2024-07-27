package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.OfferDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OffersFacadeTest {

    private final InMemoryOfferRepositoryTestImpl testRepository = new InMemoryOfferRepositoryTestImpl();
    OffersFacade offersFacade = new OffersFacadeConfiguration().createForTest(testRepository);

    @Test
    void testFindAll_whenNoneOffersFound_thenEmptyListReturned() {
        // given
        // when
        List<OfferDto> actualOffers = offersFacade.findAllOffers();

        // then
        assertThat(actualOffers).isEmpty();
    }

    @Test
    void testFindAll_whenAllOffersFound_thenSuccess() {

        // given
        Offer offer1 = new Offer(UUID.randomUUID().toString(),
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        Offer offer2 = new Offer(UUID.randomUUID().toString(),
                "Ssangyong",
                "Java Developer",
                "15 000 - 17 000 PLN",
                "https://ssangyong-auto.pl/");

        testRepository.saveAll(List.of(offer1, offer2));

        // when
        List<OfferDto> actualOffers = offersFacade.findAllOffers();

        // then
        OfferDto expectedOffer1 = OfferMapper.mapFromOffer(offer1);
        OfferDto expectedOffer2 = OfferMapper.mapFromOffer(offer2);

        assertThat(actualOffers).containsExactlyInAnyOrder(expectedOffer1, expectedOffer2);
    }

    @Test
    void testFindById_whenOfferWithGivenIdFound_thenSuccess() {

        //given
        final String OFFER_ID = UUID.randomUUID().toString();
        Offer offer = new Offer(OFFER_ID,
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        testRepository.save(offer);

        // when
        OfferDto actualOfferDto = offersFacade.findOfferById(OFFER_ID);

        // then
        OfferDto expectedOfferDto = OfferMapper.mapFromOffer(offer);

        assertThat(actualOfferDto).isEqualTo(expectedOfferDto);
    }

    @Test
    void testFindById_whenOfferWithGivenIdNotFound_thenThrowsOfferNotFoundException() {

        //given
        final String NOT_PRESENT_ID = UUID.randomUUID().toString();
        Offer offer = new Offer(UUID.randomUUID().toString(),
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        testRepository.save(offer);
        // when
        // then
        final String OFFER_NOT_FOUND = OfferExceptionMessageBuilder.buildOfferNotFoundMessage(NOT_PRESENT_ID);

        assertThatThrownBy(() -> offersFacade.findOfferById(NOT_PRESENT_ID))
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(OFFER_NOT_FOUND);
    }

    @Test
    void testSave_whenSavingNewOffer_thenSuccess() {
        // given
        final String ID = UUID.randomUUID().toString();
        final Offer offer = new Offer(ID,
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        OfferDto mappedFromOffer = OfferMapper.mapFromOffer(offer);

        // when
        OfferDto actualOfferDto = offersFacade.saveOffer(mappedFromOffer);

        // then
        OfferDto expectedOfferDto = OfferMapper.mapFromOffer(offer);

        assertThat(actualOfferDto).isEqualTo(expectedOfferDto);
        assertThat(testRepository.existsById(ID)).isTrue();
    }

    @Test
    void testSave_whenSavingOfferThatAlreadyExists_thenThrowsOfferAlreadyExistsException() {

        // given
        final String ID = UUID.randomUUID().toString();
        final Offer offer = new Offer(ID,
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");

        testRepository.save(offer);
        OfferDto mappedFromOffer = OfferMapper.mapFromOffer(offer);

        // when
        // then
        final String OFFER_ALREADY_EXISTS = OfferExceptionMessageBuilder.buildOfferAlreadyExistsMessage(offer);

        assertThatThrownBy(() -> offersFacade.saveOffer(mappedFromOffer))
                .isInstanceOf(OfferAlreadyExistsException.class)
                .hasMessageContaining(OFFER_ALREADY_EXISTS);
    }

}