package com.telekom.demo.call;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {
    @LocalServerPort
    protected Integer port;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    HttpHeaders httpHeaders() { // sub: 1, exp: 2024
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzA3Nzc4NDExfQ.hIjYs7_VJO3bbbu5XDpP-y0dVPYgyiWXz5rVyGIhNSdaYsoe8B3-F7mYQhUl-_aZ0XBzW4M-7ScVyZ0vmzi1vQ");
        return headers;
    }
}
