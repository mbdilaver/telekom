package com.telekom.demo.infra.rest.response;

import com.telekom.demo.domain.model.Calls;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CallsResponse {

    private List<CallResponse> callResponseList;

    public static CallsResponse from(Calls calls) {
        return CallsResponse.builder()
                .callResponseList(calls.getCallList().stream()
                        .map(CallResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
