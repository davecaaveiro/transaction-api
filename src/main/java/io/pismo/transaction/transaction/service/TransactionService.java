package io.pismo.transaction.transaction.service;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.mapper.TransactionMapper;
import io.pismo.transaction.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Mono<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {

        return transactionRepository.save(TransactionMapper.toTransaction(transactionRequest))
                .map(TransactionMapper::toTransactionResponse);
    }

}