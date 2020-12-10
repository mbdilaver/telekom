package com.telekom.demo.domain.common;

import lombok.Getter;

@Getter
public enum ExceptionType {
    NO_MISSED_CALLS(1001L, "There is no missed calls");

    private final Long code;
    private final String message;

    ExceptionType(Long code, String message) {
        this.code = code;
        this.message = message;
    }
}
