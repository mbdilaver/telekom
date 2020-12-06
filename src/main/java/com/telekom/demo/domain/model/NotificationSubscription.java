package com.telekom.demo.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSubscription {

    private final String number;

    public static NotificationSubscription from(String number) {
        return NotificationSubscription.builder()
                .number(number)
                .build();
    }
}
