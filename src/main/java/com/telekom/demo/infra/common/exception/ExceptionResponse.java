package com.telekom.demo.infra.common.exception;

import com.telekom.demo.domain.common.ExceptionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private final Long errorCode;
    private final String errorMessage;

    public static ExceptionResponse from(ExceptionType exceptionType) {
        return ExceptionResponse.builder()
                .errorCode(exceptionType.getCode())
                .errorMessage(exceptionType.getMessage())
                .build();
    }
}
