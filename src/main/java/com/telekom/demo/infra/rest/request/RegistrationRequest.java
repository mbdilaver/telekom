package com.telekom.demo.infra.rest.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class RegistrationRequest {

    @Size(min = 9, max = 10)
    private String destinationNumber;

}
