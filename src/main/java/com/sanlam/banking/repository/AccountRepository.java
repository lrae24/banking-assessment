package com.sanlam.banking.repository;

import com.sanlam.banking.entitities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<BigDecimal> findBalanceById(Long accountId);

    @Modifying
    int updateBalanceById(Long id, BigDecimal amount);
}

