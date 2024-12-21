package io.pismo.transaction.transaction;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    @PostMapping
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {

        logger.info("Creating transaction for account id: {}", transactionRequest.getAccountId());

        return transactionService.createTransaction(transactionRequest).map(transactionResponse ->
                ResponseEntity.created(URI.create("/api/transactions/" + transactionResponse.getTransactionId())).body(transactionResponse)
        );
    }

}
