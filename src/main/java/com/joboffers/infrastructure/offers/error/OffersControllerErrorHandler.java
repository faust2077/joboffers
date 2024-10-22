package com.joboffers.infrastructure.offers.error;

import com.joboffers.domain.offers.OfferNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class OffersControllerErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(OfferNotFoundException.class)
    public OffersErrorResponse handleOfferNotFoundException(OfferNotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new OffersErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}
