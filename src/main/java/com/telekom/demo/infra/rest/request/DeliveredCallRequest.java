package com.telekom.demo.infra.rest.request;

import com.telekom.demo.domain.model.Call;
import com.telekom.demo.domain.model.Calls;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DeliveredCallRequest {

    private String targetNumber;
    private List<Long> callIdList;

}
