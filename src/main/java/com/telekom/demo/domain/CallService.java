package com.telekom.demo.domain;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.port.CallPort;
import com.telekom.demo.domain.port.MessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CallService {

    private final MessagePort messagePort;
    private final CallPort callPort;
    private final CallValidator callValidator;

    public Call call(Call incomingCall) {
        return callPort.makeCall(incomingCall);
    }

    public void checkNotifications(String number) {
        CallNotification missedCalls = callPort.getMissedCalls(number);

        if (checkIfHaveMissedCall(missedCalls)) {
            messagePort.publishNotifications(missedCalls);
        }
    }

    private boolean checkIfHaveMissedCall(CallNotification missedCalls) {
        return missedCalls.getCallList().size() > 0;
    }

    public void approveCalls(String targetNumber, List<String> destinationNumbers, Locale locale) {
        callValidator.validateMissedCallsExist(targetNumber, destinationNumbers);

        Calls calls = callPort.getCalls(targetNumber, destinationNumbers, false);

        callPort.approveCalls(extractIds(calls));
        messagePort.notifyCallers(calls, locale);
    }

    private List<Long> extractIds(Calls calls) {
        return calls.getCallList().stream().map(Call::getId).collect(Collectors.toList());
    }

}
