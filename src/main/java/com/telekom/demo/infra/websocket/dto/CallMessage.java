package com.telekom.demo.infra.websocket.dto;

import com.telekom.demo.domain.model.Call;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallMessage {

    private Long id;
    private LocalDateTime createdDate;
    private String destinationNumber;
    private String targetNumber;
    private Boolean isDelivered;

    public static CallMessage from(Call call) {
        return CallMessage.builder()
                .id(call.getId())
                .createdDate(call.getCreatedDate())
                .destinationNumber(call.getDestinationNumber())
                .targetNumber(call.getTargetNumber())
                .isDelivered(call.getIsDelivered())
                .build();
    }
}
