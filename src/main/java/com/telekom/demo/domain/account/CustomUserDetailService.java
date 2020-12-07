package com.telekom.demo.domain.account;

import com.telekom.demo.domain.account.model.Account;
import com.telekom.demo.domain.account.model.AccountPrincipal;
import com.telekom.demo.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String number) {
        Account account = repository.findByNumber(number);

        return new AccountPrincipal(account);
    }
}
