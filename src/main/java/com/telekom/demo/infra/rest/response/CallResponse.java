package com.telekom.demo.infra.rest.response;

import com.telekom.demo.domain.model.Call;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CallResponse {

    private final Long id;
    private final Boolean isDelivered;
    private final String destinationNumber;
    private final String targetNumber;
    private final LocalDateTime createdDate;

    public static CallResponse from(Call call) {
        return CallResponse.builder()
                .id(call.getId())
                .destinationNumber(call.getDestinationNumber())
                .targetNumber(call.getTargetNumber())
                .createdDate(call.getCreatedDate())
                .isDelivered(call.getIsDelivered())
                .build();
    }
}
