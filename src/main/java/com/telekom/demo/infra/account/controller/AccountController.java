package com.telekom.demo.infra.account.controller;


import com.telekom.demo.domain.account.AccountService;
import com.telekom.demo.domain.account.model.Account;
import com.telekom.demo.infra.account.controller.request.SignUpRequest;
import com.telekom.demo.infra.account.controller.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody SignUpRequest request) {
        Account account = service.createAccount(request.toModel());
        return AccountResponse.from(account);
    }

    @GetMapping("/{id}")
    public AccountResponse retrieve(@PathVariable String id) {
        Account account = service.retrieve(id);
        return AccountResponse.from(account);
    }
}
