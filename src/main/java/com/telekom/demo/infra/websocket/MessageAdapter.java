package com.telekom.demo.infra.websocket;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.port.MessagePort;
import com.telekom.demo.infra.websocket.dto.AvailableNotificationMessage;
import com.telekom.demo.infra.websocket.dto.MissedNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageAdapter implements MessagePort {

    private final SimpMessagingTemplate template;

    @Override
    public void publishNotifications(CallNotification subscription) {
        MissedNotificationMessage notificationMessage = MissedNotificationMessage.from(subscription);
        template.convertAndSend("/notifications/" + subscription.getTargetNumber(), notificationMessage);
    }

    @Override
    public void notifyCallers(Calls calls) {
        calls.getCallList().forEach(this::sendAvailableNotification);
    }

    private void sendAvailableNotification(Call call) {
        template.convertAndSend(AvailableNotificationMessage.from(call));
    }
}
