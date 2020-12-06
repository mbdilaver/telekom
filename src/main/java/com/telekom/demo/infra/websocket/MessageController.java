package com.telekom.demo.infra.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/notifications/{number}")
    public void send(@DestinationVariable String number) throws Exception {


    }
}
