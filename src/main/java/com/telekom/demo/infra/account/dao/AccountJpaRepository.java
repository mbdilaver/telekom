package com.telekom.demo.infra.account.dao;

import com.telekom.demo.infra.account.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, String> {

}
