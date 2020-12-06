package com.telekom.demo.infra.websocket.dto;

import com.telekom.demo.domain.model.Call;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableNotificationMessage {

    private String targetNumber;
    private LocalDateTime createdDate;

    public static AvailableNotificationMessage from(Call call) {
        return AvailableNotificationMessage.builder()
                .targetNumber(call.getTargetNumber())
                .createdDate(call.getCreatedDate())
                .build();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
