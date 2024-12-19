package com.joboffers.infrastructure.offers.controller.error;

import com.joboffers.domain.offers.OfferNotFoundException;
import com.joboffers.infrastructure.offers.controller.OffersRestController;
import lombok.extern.log4j.Log4j2;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = OffersRestController.class)
@Log4j2
public class OffersControllerErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(OfferNotFoundException.class)
    public OffersGetErrorResponse handleOfferNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new OffersGetErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public OffersPostErrorResponse handleDuplicateKeyException() {
        final String message = "Offer with given url already exists";
        log.error(message);
        return new OffersPostErrorResponse(message, HttpStatus.CONFLICT);
    }
}
