package com.telekom.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
