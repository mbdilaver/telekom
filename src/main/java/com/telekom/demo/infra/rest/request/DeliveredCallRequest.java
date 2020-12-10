package com.telekom.demo.infra.rest.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class DeliveredCallRequest {

    @Size(min = 9, max = 10)
    private String targetNumber;

    @NotEmpty
    private List<String> destinationNumbers;
}
