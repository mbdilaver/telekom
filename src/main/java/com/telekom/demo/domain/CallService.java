package com.telekom.demo.domain;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.model.NotificationSubscription;
import com.telekom.demo.domain.port.CallPort;
import com.telekom.demo.domain.port.MessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallService {

    private final MessagePort messagePort;
    private final CallPort callPort;

    public Call call(Call incomingCall) {
        return callPort.makeCall(incomingCall);
    }

    public void checkNotifications(NotificationSubscription subscription) {
        CallNotification missedCalls = callPort.getMissedCalls(subscription);
        messagePort.publishNotifications(missedCalls);
    }

    public void approveCalls(List<Long> ids) {
        callPort.approveCalls(ids);
    }

    public void notifyMissedCallers(List<Long> callIds) {
        Calls calls = callPort.getCallsById(callIds);
        messagePort.notifyCallers(calls);
    }
}
