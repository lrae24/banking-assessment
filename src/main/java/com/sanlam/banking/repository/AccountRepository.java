package com.sanlam.banking.repository;

import com.sanlam.banking.entitity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<BigDecimal> findBalanceById(Long accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Account b SET b.balance = :amount WHERE b.id = :id")
    int updateBalanceById(Long id, BigDecimal amount);
}

