package com.telekom.demo.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Call {

    private Long id;
    private String destinationNumber;
    private String targetNumber;
    private LocalDateTime createdDate;
    private Boolean isDelivered;
}
