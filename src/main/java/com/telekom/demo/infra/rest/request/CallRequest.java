package com.telekom.demo.infra.rest.request;

import com.telekom.demo.domain.model.Call;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CallRequest {

    @Size(min = 9, max = 10)
    private String destinationNumber;
    @Size(min = 9, max = 10)
    private String targetNumber;

    public Call toModel() {
        return Call.builder()
                .destinationNumber(destinationNumber)
                .targetNumber(targetNumber)
                .build();
    }

}
