package com.telekom.demo.infra.websocket;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.port.MessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

@Service
@RequiredArgsConstructor
public class MessageAdapter implements MessagePort {

    private final MessageSource messageSource;
    private final Locale defaultLocale;
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

        String header = messageSource.getMessage("notification.message", null, defaultLocale);

        return header + numbers;
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd HH:mm");
        return date.format(formatter);
    }

    @Override
    public void notifyCallers(Calls calls, Locale locale) {
        calls.getCallList().forEach(call -> sendAvailableNotification(call, locale));
    }

    private void sendAvailableNotification(Call call, Locale locale) {
        String message = createAvailableMessage(call, locale);

        template.convertAndSend("/notifications/" + call.getDestinationNumber(), message);
    }

    private String createAvailableMessage(Call call, Locale locale) {
        String format = messageSource.getMessage("available.message", null, locale);

        return String.format(
                format,
                call.getTargetNumber(),
                formatDate(call.getCreatedDate()
                ));
    }
}
