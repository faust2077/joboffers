package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.HttpResponseOfferDto;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OffersFacadeTest {

    private final OffersFacade offersFacade = new OffersFacadeTestConfiguration()
            .createOffersFacadeForTest();

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
        OfferRequestDto offerRequestDto1 = new OfferRequestDto("Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        OfferRequestDto offerRequestDto2 = new OfferRequestDto("Ssangyong",
                "Java Developer",
                "15 000 - 17 000 PLN",
                "https://ssangyong-auto.pl/");
        OfferRequestDto offerRequestDto3 = new OfferRequestDto("Oracle",
                "Java GC Engineer",
                "18 000 - 25 000 PLN",
                "https://www.oracle.com/pl/");
        OfferRequestDto offerRequestDto4 = new OfferRequestDto("Motorola",
                "Java Developer",
                "13 000 - 15 000 PLN",
                "https://motorola.com/");

        OfferResponseDto offerResponseDto1 = offersFacade.saveOffer(offerRequestDto1);
        OfferResponseDto offerResponseDto2 = offersFacade.saveOffer(offerRequestDto2);
        OfferResponseDto offerResponseDto3 = offersFacade.saveOffer(offerRequestDto3);
        OfferResponseDto offerResponseDto4 = offersFacade.saveOffer(offerRequestDto4);

        // when
        List<OfferResponseDto> actualOffers = offersFacade.findAllOffers();

        // then

        final int EXPECTED_SIZE = 4;

        assertThat(actualOffers).hasSize(EXPECTED_SIZE);
        assertThat(actualOffers).containsExactlyInAnyOrder(
                offerResponseDto1,
                offerResponseDto2,
                offerResponseDto3,
                offerResponseDto4
        );
    }

    @Test
    void testFindById_whenOfferWithGivenIdFound_thenSuccess() {

        //given
        final OfferRequestDto offerRequestDto = new OfferRequestDto("Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        final String id = offersFacade.saveOffer(offerRequestDto)
                .id();

        // when
        OfferResponseDto actualOfferResponseDto = offersFacade.findOfferById(id);

        // then
        OfferResponseDto expectedOfferResponseDto = new OfferResponseDto(actualOfferResponseDto.id(),
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");

        assertThat(actualOfferResponseDto).isEqualTo(expectedOfferResponseDto);
    }

    @Test
    void testFindById_whenOfferWithGivenIdNotFound_thenThrowsOfferNotFoundException() {

        //given
        final String NOT_PRESENT_ID = UUID.randomUUID()
                .toString();

        OfferRequestDto offerRequestDto = new OfferRequestDto(
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");

        offersFacade.saveOffer(offerRequestDto);
        assertThat(offersFacade.findAllOffers()).hasSize(1);

        // when
        Throwable thrown = catchThrowable(() -> offersFacade.findOfferById(NOT_PRESENT_ID));

        // then
        final String OFFER_NOT_FOUND = OfferExceptionMessageBuilder.buildOfferNotFoundMessage(NOT_PRESENT_ID);

        assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessageContaining(OFFER_NOT_FOUND);
    }

    @Test
    void testSave_whenSavingNewOffer_thenSuccess() {

        // given
        final OfferRequestDto offerRequestDto = new OfferRequestDto("Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");

        // when
        OfferResponseDto actualOfferResponseDto = offersFacade.saveOffer(offerRequestDto);

        // then
        OfferResponseDto actualOfferById = offersFacade.findOfferById(actualOfferResponseDto.id());
        OfferResponseDto expectedOfferResponseDto = new OfferResponseDto(actualOfferResponseDto.id(),
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");

        assertThat(actualOfferById).isEqualTo(expectedOfferResponseDto);
        assertThat(actualOfferResponseDto).isEqualTo(expectedOfferResponseDto);
    }

    @Test
    void testSave_whenSavingOfferThatAlreadyExistsByUrl_thenThrowsDuplicateKeyException() {
        // given
        final String URL = "someURL";
        final OfferRequestDto offer = new OfferRequestDto(
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                URL);
        final OfferRequestDto doppelganger = new OfferRequestDto(
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                URL);

        // when
        offersFacade.saveOffer(offer);
        Throwable thrown = catchThrowable(() -> offersFacade.saveOffer(doppelganger));

        // then
        final String OFFER_ALREADY_EXISTS = OfferExceptionMessageBuilder.buildDuplicateKeyMessage(offer.url());

        assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessageContaining(OFFER_ALREADY_EXISTS);
    }

    @Test
    void testFetchAllThenSave_whenAllOffersFetched_andOnlyOffersWithNewUrlSaved_thenSuccess() {

        // given
        HttpResponseOfferDto jobOffer1 = new HttpResponseOfferDto("a",
                "Samsung",
                "Java CMS Developer",
                "10 000 - 15 000 PLN",
                "https://www.samsung.com/pl/");
        HttpResponseOfferDto jobOffer2 = new HttpResponseOfferDto("b",
                "Ssangyong",
                "Java Android Auto Developer",
                "15 000 - 17 000 PLN",
                "https://ssangyong-auto.pl/");
        HttpResponseOfferDto jobOffer3 = new HttpResponseOfferDto("UUID.randomUUID().toString()",
                "Oracle",
                "Java GC Engineer",
                "18 000 - 25 000 PLN",
                "https://www.oracle.com/pl/");
        HttpResponseOfferDto jobOffer4 = new HttpResponseOfferDto("10",
                "Motorola",
                "Java Mobile Developer",
                "13 000 - 15 000 PLN",
                "https://motorola.com/");

        HttpResponseOfferDto newJobOffer1 = new HttpResponseOfferDto(UUID.randomUUID()
                .toString(),
                "Vimeo",
                "Java Movie Player Developer",
                "19 000 - 22 000 PLN",
                "youtube.com");
        HttpResponseOfferDto newJobOffer2 = new HttpResponseOfferDto(UUID.randomUUID()
                .toString(),
                "Go",
                "Java Google Developer",
                "19 000 - 24 000 PLN",
                "google.com");
        HttpResponseOfferDto newJobOffer3 = new HttpResponseOfferDto(UUID.randomUUID()
                .toString(),
                "Allegretto",
                "Java Allegretto Developer",
                "9 000 - 11 000 PLN",
                "allegretto.pl");

        final List<HttpResponseOfferDto> REMOTE_JOB_OFFERS = List.of(
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
                )
                .createOffersFacadeForTest();

        final int EXPECTED_SIZE_AFTER_JUST_SAVE = 4;
        assertThat(offersFacadeForRemoteTest.findAllOffers()).hasSize(EXPECTED_SIZE_AFTER_JUST_SAVE);

        // when
        List<OfferResponseDto> actualJobOffersDTOsFetched = offersFacadeForRemoteTest.fetchAllThenSave();

        // then
        OfferResponseDto expectedOffer1 = TestOnlyOfferMapper.mapFromHttpResponseOfferDtoToOfferResponseDto(newJobOffer1);
        OfferResponseDto expectedOffer2 = TestOnlyOfferMapper.mapFromHttpResponseOfferDtoToOfferResponseDto(newJobOffer2);
        OfferResponseDto expectedOffer3 = TestOnlyOfferMapper.mapFromHttpResponseOfferDtoToOfferResponseDto(newJobOffer3);

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
        final List<HttpResponseOfferDto> EMPTY_LIST = Collections.emptyList();
        OffersFacade offersFacade = new OffersFacadeTestConfiguration(EMPTY_LIST)
                .createOffersFacadeForTest();

        // when
        List<OfferResponseDto> actualJobOffersDTOsFetched = offersFacade.fetchAllThenSave();

        // then
        assertThat(actualJobOffersDTOsFetched).isEmpty();
    }

}