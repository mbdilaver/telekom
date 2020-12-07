package com.telekom.demo.infra.account.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty
    private String number;

    @NotEmpty
    private String password;
}
