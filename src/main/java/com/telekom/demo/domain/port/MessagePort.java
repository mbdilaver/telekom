package com.telekom.demo.domain.port;

import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;

public interface MessagePort {

    void publishNotifications(CallNotification subscription);

    void notifyCallers(Calls calls);
}
