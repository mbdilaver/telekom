package com.telekom.demo.infra.rest.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class RegistrationRequest {

    @NotBlank
    private String destinationNumber;

}
