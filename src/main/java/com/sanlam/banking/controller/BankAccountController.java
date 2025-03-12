package com.sanlam.banking.controller;

import com.sanlam.banking.services.BankingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/v1/bank")
public class BankAccountController {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);
    private final BankingService bankingService;

    @Autowired
    public BankAccountController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @Value("${sns.topicArn}")
    private String snsTopicArn;


    @Operation(summary = "Perform a withdrawal")
    @PostMapping("/withdraw")
    @Transactional
    public String withdraw(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        try {
            Optional<BigDecimal> currentBalance = bankingService.getCurrentBalance(accountId);
            if (currentBalance.isEmpty()) {
                return "Account not found";
            }

            BigDecimal balance = currentBalance.get();
            if (balance.compareTo(amount) <= 0) {
                return "Insufficient funds for withdrawal";
            }

            if (bankingService.updateAccountBalance(accountId, amount)) {
                bankingService.publishWithdrawalEvent(amount, accountId);
                return "Withdrawal successful";
            } else {
                return "Withdrawal failed";
            }
        } catch (Exception e) {
            logger.error("Error during withdrawal operation", e);
            return "An error occurred while processing the withdrawal";
        }
    }
}

