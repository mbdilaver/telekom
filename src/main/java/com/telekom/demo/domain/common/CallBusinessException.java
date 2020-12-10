package com.telekom.demo.domain.common;

import lombok.Getter;

@Getter
public class CallBusinessException extends RuntimeException {
    private final ExceptionType exceptionType;

    public CallBusinessException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}

