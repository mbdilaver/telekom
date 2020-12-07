package com.telekom.demo.domain.account.repository;

import com.telekom.demo.domain.account.model.Account;

public interface AccountRepository {

    Account create(Account account);

    Account retrieve(String id);

    Account findByNumber(String number);
}
