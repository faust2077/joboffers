package com.joboffers.domain.offers;

import com.joboffers.domain.offers.dto.JobOfferDto;

import java.util.List;
import java.util.UUID;

class OffersFacadeTestConfiguration {

    private final OffersFetchAndSaveService offersFetchAndSaveService;
    private final OfferRepository offerRepository;

    public OffersFacadeTestConfiguration() {
        this.offerRepository = new InMemoryOfferRepositoryTestImpl();
        this.offersFetchAndSaveService = new OffersFetchAndSaveService(
                new InMemoryOfferFetcherTestImpl(
                    List.of(
                        new JobOfferDto(UUID.randomUUID().toString(), "Samsung", "Java CMS Developer", "10 000 - 15 000 PLN", "https://www.samsung.com/pl/"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Ssangyong", "Java Developer", "15 000 - 17 000 PLN", "https://ssangyong-auto.pl/"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Oracle", "Java GC Engineer", "18 000 - 25 000 PLN", "https://www.oracle.com/pl/"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Motorola", "Java Developer", "13 000 - 15 000 PLN", "https://motorola.com/"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Vimeo", "Java Movie Player Developer", "19 000 - 22 000 PLN", "youtube.com"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Go", "Java Google Developer", "19 000 - 24 000 PLN", "google.com"),
                        new JobOfferDto(UUID.randomUUID().toString(), "Allegretto", "Java Allegretto Developer", "9 000 - 11 000 PLN", "allegretto.pl")
                    )
                ),
                offerRepository
        );
    }

    public OffersFacadeTestConfiguration(List<JobOfferDto> remoteOffers) {
        this.offerRepository = new InMemoryOfferRepositoryTestImpl();
        this.offersFetchAndSaveService = new OffersFetchAndSaveService(new InMemoryOfferFetcherTestImpl(remoteOffers), offerRepository);
    }

    OffersFacade createOfferFacadeForTest() {
        return new OffersFacade(offersFetchAndSaveService, offerRepository);
    }

    OffersFacadeTestConfiguration populateRepository(List<JobOfferDto> remoteOffers) {
        List<Offer> offers = createListOfPlainOffers(remoteOffers);
        offerRepository.saveAll(offers);
        return this;
    }

    private static List<Offer> createListOfPlainOffers(List<JobOfferDto> jobOfferDTOS) {
        return jobOfferDTOS.stream()
                .map(OfferMapper::mapFromJobOfferDtoToOffer)
                .toList();
    }

}
