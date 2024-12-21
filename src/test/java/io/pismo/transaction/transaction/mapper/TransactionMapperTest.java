package io.pismo.transaction.transaction.mapper;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionMapperTest {

    static final Long ID = 1L;
    static final Long ACCOUNT_ID = 1L;
    static final Integer OPERATION_TYPE_ID = 1;
    static final Float AMOUNT = 100.50f;
    static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();

    @Test
    void toTransactionSuccess() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        Transaction expectedTransaction = Transaction.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();

        assertEquals(expectedTransaction, TransactionMapper.toTransaction(transactionRequest));
    }

    @Test
    void toTransactionResponseSuccess() {

        Transaction transaction = Transaction.builder()
                .id(ID)
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .eventDate(LOCAL_DATE_TIME_NOW)
                .build();
        TransactionResponse expectedTransactionResponse = TransactionResponse.builder()
                .transactionId(ID)
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();

        assertEquals(expectedTransactionResponse, TransactionMapper.toTransactionResponse(transaction));
    }

}
