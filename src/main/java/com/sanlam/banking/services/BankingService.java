package com.sanlam.banking.services;

import com.sanlam.banking.controller.BankAccountController;
import com.sanlam.banking.dtos.PublishRequest;
import com.sanlam.banking.dtos.PublishResponse;
import com.sanlam.banking.dtos.WithdrawalEvent;
import com.sanlam.banking.enums.WithdrawalEventStatus;
import com.sanlam.banking.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankingService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    private final AccountRepository accountRepository;
    private final SNSClientService snsClient;

    @Autowired
    public BankingService(AccountRepository accountRepository, SNSClientService snsClient) {
        this.accountRepository = accountRepository;
        this.snsClient = snsClient;
    }
    @Value("${sns.topicArn}")
    private String snsTopicArn;

    public Optional<BigDecimal> getCurrentBalance(Long accountId) {
        return accountRepository.findBalanceById(accountId);
    }

    public boolean updateAccountBalance(Long accountId, BigDecimal amount) {
        int rowsAffected = accountRepository.updateBalanceById(accountId, amount);
        return rowsAffected > 0;
    }

    public void publishWithdrawalEvent(BigDecimal amount, Long accountId) {
        WithdrawalEvent event = new WithdrawalEvent(amount, accountId, WithdrawalEventStatus.Succeessful);
        String eventJson = event.toJson(); // Convert event to JSON

        PublishRequest publishRequest = PublishRequest.builder()
                .message(eventJson)
                .topicArn(snsTopicArn)
                .build();

        try {
            PublishResponse publishResponse = snsClient.publish(publishRequest);
            logger.info("Published withdrawal event with messageId: " + publishResponse.getMessageId());
        } catch (Exception e) {
            logger.error("Error publishing withdrawal event to SNS", e);
        }
    }
}
