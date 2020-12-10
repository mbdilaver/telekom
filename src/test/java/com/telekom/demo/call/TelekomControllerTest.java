package com.telekom.demo.call;

import com.telekom.demo.infra.rest.request.CallRequest;
import com.telekom.demo.infra.rest.response.CallResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.StringMessageConverter;
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
    private BlockingQueue<String> messageBlockingQueue;
    private WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        messageBlockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new StringMessageConverter());

    }

    @Test
    public void should_getMissedCalls() throws InterruptedException, ExecutionException, TimeoutException {
        // given
        String targetNumber = "123456788";
        String destinationNumber = "123456786";

        CallRequest request = new CallRequest();
        request.setDestinationNumber(destinationNumber);
        request.setTargetNumber(targetNumber);

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

        session.subscribe("/notifications/" + targetNumber, new MessageStompFrameHandler());

        //check message
        String notification = messageBlockingQueue.poll(2, SECONDS);

        assertThat(notification).isNotBlank();
        assertThat(StringUtils.containsIgnoreCase(notification, destinationNumber)).isTrue();
    }

    @Test
    public void should_getAvailableNotification() throws InterruptedException, ExecutionException, TimeoutException {
        // given
        String targetNumber = "123456788";
        String destinationNumber = "123456786";

        CallRequest request = new CallRequest();
        request.setDestinationNumber(destinationNumber);
        request.setTargetNumber(targetNumber);

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

        session.subscribe("/notifications/" + targetNumber, new MessageStompFrameHandler());

        //check message
        String notification = messageBlockingQueue.poll(2, SECONDS);

        assertThat(notification).isNotBlank();
        assertThat(StringUtils.containsIgnoreCase(notification, destinationNumber)).isTrue();
    }

    class MessageStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            messageBlockingQueue.add((String) o);
        }
    }
}
