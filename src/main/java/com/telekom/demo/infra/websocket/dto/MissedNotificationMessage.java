package com.telekom.demo.infra.websocket.dto;

import com.telekom.demo.domain.model.CallNotification;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MissedNotificationMessage {

    private String targetNumber;
    private List<CallMessage> callList;

    public static MissedNotificationMessage from(CallNotification notification) {
        return MissedNotificationMessage.builder()
                .targetNumber(notification.getTargetNumber())
                .callList(extractCallList(notification))
                .build();
    }

    private static List<CallMessage> extractCallList(CallNotification notification) {
        return notification.getCallList().stream().map(CallMessage::from).collect(Collectors.toList());
    }
}
