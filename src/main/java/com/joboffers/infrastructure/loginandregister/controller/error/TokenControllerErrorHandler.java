package com.joboffers.infrastructure.loginandregister.controller.error;

import com.joboffers.infrastructure.loginandregister.controller.TokenRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = TokenRestController.class)
@Log4j2
public class TokenControllerErrorHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public TokenErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new TokenErrorResponse(message, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public TokenErrorResponse handleDuplicateKeyException() {
        final String message = "User with given username already exists";
        log.error(message);
        return new TokenErrorResponse(message, HttpStatus.CONFLICT);
    }

}
