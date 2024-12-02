package com.joboffers.infrastructure.offers.apivalidation;

import com.joboffers.infrastructure.offers.controller.OffersRestController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice (basePackageClasses = OffersRestController.class)
public class OfferValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public OfferValidationErrorDto handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = getErrorMessagesFromException(exception);
        return new OfferValidationErrorDto(errors, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorMessagesFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
