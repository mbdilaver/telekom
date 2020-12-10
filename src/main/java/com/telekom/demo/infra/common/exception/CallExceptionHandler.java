package com.telekom.demo.infra.common.exception;

import com.telekom.demo.domain.common.CallBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CallExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CallBusinessException.class)
    public ExceptionResponse handleException(CallBusinessException exception) {
        return ExceptionResponse.from(exception.getExceptionType());
    }
}
