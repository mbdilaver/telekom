package com.telekom.demo.infra.account.dao.entity;


import com.telekom.demo.domain.account.model.Account;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "account")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account")
@Data
public class AccountEntity {

    @Id
    private String number;

    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private String password;

    public static AccountEntity from(Account account) {
        AccountEntity entity = new AccountEntity();
        entity.setNumber(account.getNumber());
        entity.setPassword(account.getPassword());
        return entity;
    }

    public Account toModel() {
        return Account.builder()
                .number(number)
                .password(password)
                .build();
    }
}
