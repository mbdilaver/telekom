package com.telekom.demo.infra.account.dao;

import com.telekom.demo.domain.account.model.Account;
import com.telekom.demo.domain.account.repository.AccountRepository;
import com.telekom.demo.infra.account.dao.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountAdapter implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;

    @Override
    public Account create(Account account) {
        AccountEntity accountDocument = AccountEntity.from(account);
        return accountJpaRepository.save(accountDocument).toModel();
    }

    @Override
    public Account retrieve(String id) {
        return accountJpaRepository.findById(id).orElseThrow(RuntimeException::new).toModel();
    }

    @Override
    public Account findByNumber(String number) {
        return accountJpaRepository.findById(number)
                .orElseThrow(RuntimeException::new)
                .toModel();
    }
}
