package com.telekom.demo.domain.account.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.Authentication;

@Getter
@Builder
public class Account {

    private final String number;
    private String password;

    public static Account from(Authentication authentication) {
        return (Account) authentication.getPrincipal();
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public static Account from(String id) {
        return Account.builder()
                .number(id)
                .build();
    }
}
