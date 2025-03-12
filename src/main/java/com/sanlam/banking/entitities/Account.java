package com.sanlam.banking.entitities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Account {
    @Id
    private Long id;

    private BigDecimal balance;
}
