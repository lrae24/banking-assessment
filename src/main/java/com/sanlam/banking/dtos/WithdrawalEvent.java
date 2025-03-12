package com.sanlam.banking.dtos;

import com.sanlam.banking.enums.WithdrawalEventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class WithdrawalEvent {
    private BigDecimal amount;
    private Long accountId;
    private WithdrawalEventStatus status;

    // Convert to JSON String
    public String toJson() {
        return String.format("{\"amount\":\"%s\",\"accountId\":%d,\"status\":\"%s\"}", amount, accountId, status);
    }
}