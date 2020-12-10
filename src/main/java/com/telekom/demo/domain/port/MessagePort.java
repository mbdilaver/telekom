package com.telekom.demo.domain.port;

import com.telekom.demo.domain.model.CallNotification;
import com.telekom.demo.domain.model.Calls;

import java.util.Locale;

public interface MessagePort {

    void publishNotifications(CallNotification subscription);

    void notifyCallers(Calls calls, Locale locale);
}
