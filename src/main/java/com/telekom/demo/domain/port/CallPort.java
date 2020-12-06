package com.telekom.demo.domain.port;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;
import com.telekom.demo.domain.model.NotificationSubscription;

import java.util.List;

public interface CallPort {

    Call makeCall(Call call);

    CallNotification getMissedCalls(NotificationSubscription subscription);

    void approveCalls(List<Long> ids);

    Calls getCallsById(List<Long> callIds);
}
