package com.telekom.demo.infra.websocket;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.port.MessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

@Service
@RequiredArgsConstructor
public class MessageAdapter implements MessagePort {

    private final SimpMessagingTemplate template;

    @Override
    public void publishNotifications(CallNotification subscription) {
        String message = createNotificationMessage(subscription);

        template.convertAndSend("/notifications/" + subscription.getTargetNumber(), message);
    }

    private String createNotificationMessage(CallNotification subscription) {
        Map<String, Optional<Call>> lastCalls = subscription.getCallList().stream()
                .collect(groupingBy(Call::getDestinationNumber, maxBy(Comparator.comparing(Call::getCreatedDate))));

        Map<String, Long> callCounts = subscription.getCallList().stream()
                .collect(groupingBy(Call::getDestinationNumber, Collectors.counting()));

        String numbers = lastCalls.keySet().stream()
                .map(key -> String.format(
                        "%s %s %s",
                        lastCalls.get(key).get()
                                .getDestinationNumber(),
                        formatDate(lastCalls.get(key).get().getCreatedDate()),
                        callCounts.get(key)))
                .collect(Collectors.joining("\n"));

        return "Missed calls:\n" + numbers;
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm");
        return date.format(formatter);
    }

    @Override
    public void notifyCallers(Calls calls) {
        calls.getCallList().forEach(this::sendAvailableNotification);
    }

    private void sendAvailableNotification(Call call) {
        String message = createAvailableMessage(call);

        template.convertAndSend("/notifications/" + call.getDestinationNumber(), message);
    }

    private String createAvailableMessage(Call call) {
        String format = "The number you called %s at %s is now available";

        return String.format(
                format,
                call.getTargetNumber(),
                formatDate(call.getCreatedDate()
                ));
    }
}
