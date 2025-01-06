package io.pismo.transaction.transaction.service;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.mapper.TransactionMapper;
import io.pismo.transaction.transaction.model.Transaction;
import io.pismo.transaction.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final Integer WITHDRAW = 1;
    private static final Integer PAYMENT = 4;

    private final TransactionRepository transactionRepository;

    public Mono<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = TransactionMapper.toTransaction(transactionRequest);

        return getUpdatedTransaction(transaction)
                .flatMap(transactionRepository::save)
                .map(TransactionMapper::toTransactionResponse);
    }

    private Mono<Transaction> getUpdatedTransaction(Transaction currentTransaction) {

        if (WITHDRAW.equals(currentTransaction.getOperationTypeId())) {
            currentTransaction.setBalance(currentTransaction.getAmount());
        } else if (PAYMENT.equals(currentTransaction.getOperationTypeId())) {
            return payTransactionsBalanceAndUpdateAmount(currentTransaction);
        }

        return Mono.just(currentTransaction);
    }

    private Mono<Transaction> payTransactionsBalanceAndUpdateAmount(Transaction currentTransaction) {

        currentTransaction.setBalance(currentTransaction.getAmount());

        return transactionRepository.findAllTransactionsWithNegativeBalanceOrderByEventDate()
                .filter(t -> !BigDecimal.ZERO.equals(currentTransaction.getBalance()))
                .flatMap(t -> {

                    if (isAmountAbleToPayBalance(t, currentTransaction)) {
                        currentTransaction.setBalance(currentTransaction.getBalance().add(t.getBalance()));
                        t.setBalance(BigDecimal.ZERO);
                    } else {
                        t.setBalance(currentTransaction.getBalance().add(t.getBalance()));
                        currentTransaction.setBalance(BigDecimal.ZERO);
                    }

                    return transactionRepository.save(t);
                })
                .then(Mono.just(currentTransaction));
    }

    private boolean isAmountAbleToPayBalance(Transaction olderTransaction, Transaction currentTransaction) {

        return olderTransaction.getBalance().add(currentTransaction.getBalance()).compareTo(BigDecimal.ZERO) > 0;
    }

}