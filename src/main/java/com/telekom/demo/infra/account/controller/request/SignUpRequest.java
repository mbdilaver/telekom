package com.telekom.demo.infra.account.controller.request;

import com.telekom.demo.domain.account.model.Account;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @NotEmpty
    @Size(min = 9, max = 10)
    private String number;

    @NotEmpty
    private String password;

    public Account toModel() {
        return Account.builder()
                .number(number)
                .password(password)
                .build();
    }
}
