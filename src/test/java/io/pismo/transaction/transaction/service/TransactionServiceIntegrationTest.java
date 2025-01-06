package io.pismo.transaction.transaction.service;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.model.Transaction;
import io.pismo.transaction.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionServiceIntegrationTest {

    static final Long ID = 1L;
    static final Long ACCOUNT_ID = 1L;
    static final Integer OPERATION_TYPE_NORMAL_PURCHASE = 1;
    static final Integer OPERATION_TYPE_PAYMENT = 4;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void createTransactionWhenLessBalanceThanAmountThenPayAllOffSuccess() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_PAYMENT)
                .amount(BigDecimal.valueOf(60))
                .build();

        Transaction transaction1 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-50))
                .build();

        Transaction transaction2 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-23.5))
                .build();

        Transaction transaction3 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-18.7))
                .build();

        Transaction transaction4 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(60))
                .build();

        TransactionResponse expectedTransactionResponse = TransactionResponse.builder()
                .transactionId(4L)
                .accountId(ID)
                .operationTypeId(OPERATION_TYPE_PAYMENT)
                .amount(BigDecimal.valueOf(60))
                .balance(BigDecimal.ZERO)
                .build();

        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3, transaction4));

        transactionService.createTransaction(transactionRequest).flatMap(transactionResponse -> {
            assertEquals(expectedTransactionResponse, transactionResponse);
            return Mono.just(transactionResponse);
        });
    }

    @Test
    void createTransactionWhenMoreBalanceThanAmountThenAmountIsLeftSuccess() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_PAYMENT)
                .amount(BigDecimal.valueOf(100))
                .build();

        Transaction transaction1 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-50))
                .build();

        Transaction transaction2 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-13.5))
                .build();

        Transaction transaction3 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(-18.7))
                .build();

        Transaction transaction4 = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_NORMAL_PURCHASE)
                .amount(BigDecimal.valueOf(60))
                .build();

        TransactionResponse expectedTransactionResponse = TransactionResponse.builder()
                .transactionId(5L)
                .accountId(ID)
                .operationTypeId(OPERATION_TYPE_PAYMENT)
                .amount(BigDecimal.valueOf(67.8))
                .balance(BigDecimal.ZERO)
                .build();

        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3, transaction4));

        transactionService.createTransaction(transactionRequest).flatMap(transactionResponse -> {
            assertEquals(expectedTransactionResponse, transactionResponse);
            return Mono.just(transactionResponse);
        });
    }

}
