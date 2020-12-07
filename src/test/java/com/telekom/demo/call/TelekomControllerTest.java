package com.telekom.demo.call;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.telekom.demo.infra.rest.request.CallRequest;
import com.telekom.demo.infra.rest.response.CallResponse;
import com.telekom.demo.infra.websocket.dto.MissedNotificationMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;


public class TelekomControllerTest extends AbstractControllerTest {
    private BlockingQueue<MissedNotificationMessage> messageBlockingQueue;
    private WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        messageBlockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));

    }
    
    @Test
    public void should_getNotifications() throws InterruptedException, ExecutionException, TimeoutException {

        // given
        CallRequest request = new CallRequest();
        request.setDestinationNumber("1");
        request.setTargetNumber("2");

        // when
        ResponseEntity<CallResponse> response = testRestTemplate.exchange(
                "/calls",
                HttpMethod.POST,
                new HttpEntity<>(request, httpHeaders()),
                CallResponse.class);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // websocket
        String webSocketUrl = "ws://localhost:" + port + "/chat";

        StompSession session = stompClient
                .connect(webSocketUrl, new StompSessionHandlerAdapter() {
                })
                .get(1, SECONDS);

        String number = "2";
        session.subscribe("/notifications/" + number, new MessageStompFrameHandler());

        //check message
        MissedNotificationMessage notification = messageBlockingQueue.poll(2, SECONDS);

        assertThat(notification).isNotNull()
                .hasFieldOrPropertyWithValue("targetNumber", number);
        assertThat(notification.getCallList()).hasSize(1);
        assertThat(notification.getCallList().get(0)).hasFieldOrPropertyWithValue("targetNumber", number);
    }

    class MessageStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @SneakyThrows
        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            String s = new String((byte[]) o);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            messageBlockingQueue.offer(objectMapper.readValue(s, MissedNotificationMessage.class));
        }
    }
}
