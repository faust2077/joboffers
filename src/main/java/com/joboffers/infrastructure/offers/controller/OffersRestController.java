package com.joboffers.infrastructure.offers.controller;

import com.joboffers.domain.offers.OffersFacade;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersRestController {

    private final OffersFacade offersFacade;

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> findAllOffers() {
        List<OfferResponseDto> allOffers = offersFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponseDto> findOfferById(@PathVariable String id) {
        OfferResponseDto offerById = offersFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }

    @PostMapping
    public ResponseEntity<OfferResponseDto> saveOffer(OfferRequestDto offerRequestDto) {
        OfferResponseDto offerResponseDto = offersFacade.saveOffer(offerRequestDto);
        URI location = UriComponentsBuilder.fromPath("/offers/{id}")
                .buildAndExpand(offerResponseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
