package com.telekom.demo.infra.rest.request;

import com.telekom.demo.domain.model.Call;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
public class CallRequest {

    private String destinationNumber;
    private String targetNumber;

    public Call toModel() {
        return Call.builder()
                .destinationNumber(destinationNumber)
                .targetNumber(targetNumber)
                .build();
    }

}
