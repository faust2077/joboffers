package com.joboffers.infrastructure.offers.controller;

import com.joboffers.domain.offers.OffersFacade;
import com.joboffers.domain.offers.dto.OfferRequestDto;
import com.joboffers.domain.offers.dto.OfferResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OfferResponseDto> createOffer(@RequestBody @Valid OfferRequestDto offerRequestDto) {
        OfferResponseDto offerResponseDto = offersFacade.saveOffer(offerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(offerResponseDto);
    }

}
