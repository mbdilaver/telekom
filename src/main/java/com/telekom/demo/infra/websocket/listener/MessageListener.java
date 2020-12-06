package com.telekom.demo.infra.websocket.listener;

import com.telekom.demo.domain.CallService;
import com.telekom.demo.domain.model.NotificationSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class MessageListener {
    private final CallService callService;

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        Message<byte[]> message = event.getMessage();
        String simpDestination = String.valueOf(message.getHeaders().get("simpDestination"));

        if (simpDestination != null && simpDestination.startsWith("/notifications")) {
            String number = getNumberFromDestination(simpDestination);

            NotificationSubscription subscription = NotificationSubscription.from(number);
            callService.checkNotifications(subscription);
//            callService.notifyMissedCallers(subscription);
        }
    }

    private String getNumberFromDestination(String simpDestination) {
        return simpDestination.substring(simpDestination.lastIndexOf("/") + 1, simpDestination.length());
    }
}
