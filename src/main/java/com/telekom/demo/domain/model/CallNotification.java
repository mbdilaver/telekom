package com.telekom.demo.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CallNotification {

    private final String targetNumber;
    private final List<Call> callList;
}
