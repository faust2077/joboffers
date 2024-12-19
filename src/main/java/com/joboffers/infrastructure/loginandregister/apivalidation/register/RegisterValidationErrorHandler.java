package com.joboffers.infrastructure.loginandregister.apivalidation.register;


import com.joboffers.infrastructure.loginandregister.controller.RegisterRestController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice (basePackageClasses = RegisterRestController.class)
public class RegisterValidationErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RegisterValidationErrorDto handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = getErrorMessagesFromException(exception);
        return new RegisterValidationErrorDto(errors, HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorMessagesFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
