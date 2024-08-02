package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OffersFacadeTest {

    private final OffersFacade offersFacade = new OffersFacadeTestConfiguration()
            .createOfferFacadeForTest();

    @Test
    void testFindAll_whenNoneOffersFound_thenEmptyListReturned() {
        // given
        // when
        List<OfferResponseDto> actualOffers = offersFacade.findAllOffers();

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
        Offer offer3 = new Offer(UUID.randomUUID().toString(),
                "Oracle",
                "Java GC Engineer",
                "18 000 - 25 000 PLN",
                "https://www.oracle.com/pl/");
        Offer offer4 = new Offer(UUID.randomUUID().toString(),
                "Motorola",
                "Java Developer",
                "13 000 - 15 000 PLN",
                "https://motorola.com/");

        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer1));
        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer2));
        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer3));
        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer4));

        // when
        List<OfferResponseDto> actualOffers = offersFacade.findAllOffers();

        // then
        OfferResponseDto expectedOffer1 = OfferMapper.mapFromOfferToOfferResponseDto(offer1);
        OfferResponseDto expectedOffer2 = OfferMapper.mapFromOfferToOfferResponseDto(offer2);
        OfferResponseDto expectedOffer3 = OfferMapper.mapFromOfferToOfferResponseDto(offer3);
        OfferResponseDto expectedOffer4 = OfferMapper.mapFromOfferToOfferResponseDto(offer4);

        final int EXPECTED_SIZE = 4;

        assertThat(actualOffers).hasSize(EXPECTED_SIZE);
        assertThat(actualOffers).containsExactlyInAnyOrder(
                expectedOffer1,
                expectedOffer2,
                expectedOffer3,
                expectedOffer4
        );
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
        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer));

        // when
        OfferResponseDto actualOfferResponseDto = offersFacade.findOfferById(OFFER_ID);

        // then
        OfferResponseDto expectedOfferResponseDto = OfferMapper.mapFromOfferToOfferResponseDto(offer);

        assertThat(actualOfferResponseDto).isEqualTo(expectedOfferResponseDto);
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

        offersFacade.saveOffer(OfferMapper.mapFromOfferToOfferRequestDto(offer));
        assertThat(offersFacade.findAllOffers()).hasSize(1);

        // when
        Throwable thrown = catchThrowable( () -> offersFacade.findOfferById(NOT_PRESENT_ID) );

        // then
        final String OFFER_NOT_FOUND = OfferExceptionMessageBuilder.buildOfferNotFoundMessage(NOT_PRESENT_ID);

        assertThat(thrown)
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
        OfferRequestDto mappedFromOffer = OfferMapper.mapFromOfferToOfferRequestDto(offer);

        // when
        OfferResponseDto actualOfferResponseDto = offersFacade.saveOffer(mappedFromOffer);

        // then
        OfferResponseDto actualOfferById = offersFacade.findOfferById(ID);
        OfferResponseDto expectedOfferResponseDto = OfferMapper.mapFromOfferToOfferResponseDto(offer);

        assertThat(actualOfferById).isEqualTo(expectedOfferResponseDto);
        assertThat(actualOfferResponseDto).isEqualTo(expectedOfferResponseDto);
    }

    @Test
    void testSave_whenSavingOfferThatAlreadyExistsById_thenThrowsDuplicateKeyException() {

        // given
        final String ID = UUID.randomUUID().toString();
        final Offer offer = new Offer(ID,
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        final Offer doppelganger = new Offer(ID,
                "Sushi",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "sushi.eu");

        final OfferRequestDto FIRST_OFFER_SAVED = OfferMapper.mapFromOfferToOfferRequestDto(offer);
        final OfferRequestDto OFFER_WITH_EXISTING_ID = OfferMapper.mapFromOfferToOfferRequestDto(doppelganger);

        // when
        offersFacade.saveOffer(FIRST_OFFER_SAVED);
        Throwable thrown = catchThrowable( () -> offersFacade.saveOffer(OFFER_WITH_EXISTING_ID) );

        // then
        final String DUPLICATE_KEY = OfferExceptionMessageBuilder.buildDuplicateKeyMessage(offer);

        assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessageContaining(DUPLICATE_KEY);
    }

    @Test
    void testSave_whenSavingOfferThatAlreadyExistsByUrl_thenThrowsOfferAlreadyExistsException() {
        // given
        final String URL = "someURL";
        final Offer offer = new Offer("UUID.randomUUID().toString()",
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                URL);
        final Offer doppelganger = new Offer("randomUUID",
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                URL);

        final OfferRequestDto FIRST_OFFER_SAVED = OfferMapper.mapFromOfferToOfferRequestDto(offer);
        final OfferRequestDto OFFER_WITH_EXISTING_URL = OfferMapper.mapFromOfferToOfferRequestDto(doppelganger);

        // when
        offersFacade.saveOffer(FIRST_OFFER_SAVED);
        Throwable thrown = catchThrowable( () -> offersFacade.saveOffer(OFFER_WITH_EXISTING_URL) );

        // then
        final String OFFER_ALREADY_EXISTS = OfferExceptionMessageBuilder.buildOfferAlreadyExistsMessage(offer);

        assertThat(thrown)
                .isInstanceOf(OfferAlreadyExistsException.class)
                .hasMessageContaining(OFFER_ALREADY_EXISTS);
    }

    @Test
    void testFetchAllThenSave_whenAllOffersFetched_andOnlyOffersWithNewUrlSaved_thenSuccess() {

        // given
        JobOfferDto jobOffer1 = new JobOfferDto("a",
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        JobOfferDto jobOffer2 = new JobOfferDto("b",
                "Ssangyong",
                "Java Android Auto Developer",
                "15 000 - 17 000 PLN",
                "https://ssangyong-auto.pl/");
        JobOfferDto jobOffer3 = new JobOfferDto("UUID.randomUUID().toString()",
                "Oracle",
                "Java GC Engineer",
                "18 000 - 25 000 PLN",
                "https://www.oracle.com/pl/");
        JobOfferDto jobOffer4 = new JobOfferDto("10",
                "Motorola",
                "Java Mobile Developer",
                "13 000 - 15 000 PLN",
                "https://motorola.com/");

        JobOfferDto newJobOffer1 = new JobOfferDto(UUID.randomUUID().toString(),
                "Vimeo",
                "Java Movie Player Developer",
                "19 000 - 22 000 PLN",
                "youtube.com");
        JobOfferDto newJobOffer2 = new JobOfferDto(UUID.randomUUID().toString(),
                "Go",
                "Java Google Developer",
                "19 000 - 24 000 PLN",
                "google.com");
        JobOfferDto newJobOffer3 = new JobOfferDto(UUID.randomUUID().toString(),
                "Allegretto",
                "Java Allegretto Developer",
                "9 000 - 11 000 PLN",
                "allegretto.pl");

        final List<JobOfferDto> REMOTE_JOB_OFFERS = List.of(
                jobOffer1,
                jobOffer2,
                jobOffer3,
                jobOffer4,
                newJobOffer1,
                newJobOffer2,
                newJobOffer3
        );

        OffersFacade offersFacadeForRemoteTest = new OffersFacadeTestConfiguration(REMOTE_JOB_OFFERS).populateRepository(
                List.of(
                        jobOffer1,
                        jobOffer2,
                        jobOffer3,
                        jobOffer4
                )
        ).createOfferFacadeForTest();

        final int EXPECTED_SIZE_AFTER_JUST_SAVE = 4;
        assertThat(offersFacadeForRemoteTest.findAllOffers()).hasSize(EXPECTED_SIZE_AFTER_JUST_SAVE);

        // when
        List<OfferResponseDto> actualJobOffersDTOsFetched = offersFacadeForRemoteTest.fetchAllThenSave();

        // then
        OfferResponseDto expectedOffer1 = OfferMapper.mapFromJobOfferDtoToOfferResponseDto(newJobOffer1);
        OfferResponseDto expectedOffer2 = OfferMapper.mapFromJobOfferDtoToOfferResponseDto(newJobOffer2);
        OfferResponseDto expectedOffer3 = OfferMapper.mapFromJobOfferDtoToOfferResponseDto(newJobOffer3);

        final var LIST_OF_ACTUAL_URLS = actualJobOffersDTOsFetched.stream()
                .map(OfferResponseDto::url)
                .toList();

        assertThat(LIST_OF_ACTUAL_URLS).containsExactlyInAnyOrder(
                expectedOffer1.url(), expectedOffer2.url(), expectedOffer3.url()
        );
    }

    @Test
    void testFetchAllThenSave_whenNoneOffersFetched_thenNoneOffersSaved_andEmptyListReturned() {

        // given
        final List<JobOfferDto> EMPTY_LIST = List.of();
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(EMPTY_LIST)
                .createOfferFacadeForTest();

        // when
        List<OfferResponseDto> actualJobOffersDTOsFetched = offersFacade.fetchAllThenSave();

        // then
        assertThat(actualJobOffersDTOsFetched).isEmpty();
    }

}