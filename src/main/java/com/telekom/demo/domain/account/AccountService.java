package com.telekom.demo.domain.account;

import com.telekom.demo.domain.account.model.Account;
import com.telekom.demo.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Account createAccount(Account account) {

        account.encodePassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return repository.create(account);
    }

    public Account retrieve(String id) {
        return repository.retrieve(id);
    }
}
