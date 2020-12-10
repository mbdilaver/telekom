package com.telekom.demo.domain.port;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;

import java.util.List;

public interface CallPort {

    Call makeCall(Call call);

    CallNotification getMissedCalls(String number);

    Calls getCalls(String targetNumber, List<String> destinationNumbers, Boolean isDelivered);

    void approveCalls(List<Long> callIds);

    Boolean missedCallsExist(String targetNumber, List<String> destinationNumbers);
}
