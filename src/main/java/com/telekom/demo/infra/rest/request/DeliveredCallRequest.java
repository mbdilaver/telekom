package com.telekom.demo.infra.rest.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class DeliveredCallRequest {

    private List<Long> callIdList;
}
