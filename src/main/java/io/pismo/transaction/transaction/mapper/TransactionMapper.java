package io.pismo.transaction.transaction.mapper;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.model.Transaction;

public class TransactionMapper {

    public static Transaction toTransaction(TransactionRequest transactionRequest) {

        return Transaction.builder()
                .accountId(transactionRequest.getAccountId())
                .operationTypeId(transactionRequest.getOperationTypeId())
                .amount(transactionRequest.getAmount())
                .build();
    }

    public static TransactionResponse toTransactionResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .accountId(transaction.getAccountId())
                .operationTypeId(transaction.getOperationTypeId())
                .amount(transaction.getAmount())
                .balance(transaction.getBalance())
                .build();
    }

}
