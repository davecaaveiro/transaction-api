package io.pismo.transaction.transaction.service;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.model.Transaction;
import io.pismo.transaction.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TransactionServiceTest {

    static final Long ID = 1L;
    static final Long ACCOUNT_ID = 1L;
    static final Integer OPERATION_TYPE_ID = 1;
    static final BigDecimal AMOUNT = BigDecimal.valueOf(100.50);
    static final BigDecimal BALANCE = BigDecimal.valueOf(100.50);

    @Autowired
    TransactionService transactionService;

    @MockitoSpyBean
    TransactionRepository transactionRepository;

    @Test
    void createTransactionSuccess() {

        Transaction transaction = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        TransactionResponse expectedTransactionResponse = TransactionResponse.builder()
                .transactionId(ID)
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .balance(BALANCE)
                .build();

        doAnswer(invocation -> Mono.just(expectedTransactionResponse)).when(transactionRepository).save(transaction);

        transactionService.createTransaction(transactionRequest).flatMap(transactionResponse -> {
            assertEquals(expectedTransactionResponse, transactionResponse);
            return Mono.just(transactionResponse);
        });
    }

    @Test
    void createTransactionFailure() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();

        doReturn(Mono.error(new RuntimeException("Exception Message"))).when(transactionRepository).save(any());

        StepVerifier.create(transactionService.createTransaction(transactionRequest))
                .expectError(RuntimeException.class)
                .verify();
    }

}
