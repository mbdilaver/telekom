package com.telekom.demo.infra.account.controller.response;

import com.telekom.demo.domain.account.model.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponse {

    private final String number;

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
                .number(account.getNumber())
                .build();
    }
}
