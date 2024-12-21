package io.pismo.transaction.transaction;

import io.pismo.transaction.transaction.dto.TransactionRequest;
import io.pismo.transaction.transaction.dto.TransactionResponse;
import io.pismo.transaction.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@AutoConfigureWebTestClient
class TransactionControllerTest {

    static final Long ID = 1L;
    static final Long ACCOUNT_ID = 1L;
    static final Integer OPERATION_TYPE_ID = 1;
    static final Float AMOUNT = 100.50f;

    @Autowired
    WebTestClient webTestClient;

    @MockitoSpyBean
    TransactionService transactionService;

    @Test
    void createTransaction201Created() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        Mono<TransactionRequest> transactionRequestMono = Mono.just(transactionRequest);
        Mono<TransactionResponse> transactionResponseMono = Mono.just(TransactionResponse.builder()
                .transactionId(ID)
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build());

        doAnswer(invocation -> transactionResponseMono).when(transactionService).createTransaction(transactionRequest);

        webTestClient.post()
                .uri("/api/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .body(transactionRequestMono, TransactionRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.transactionId")
                .isEqualTo(ID)
                .jsonPath("$.accountId")
                .isEqualTo(ACCOUNT_ID)
                .jsonPath("$.operationTypeId")
                .isEqualTo(OPERATION_TYPE_ID)
                .jsonPath("$.amount")
                .isEqualTo(AMOUNT);
    }

    @Test
    void createTransaction400BadRequest() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(null)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        Mono<TransactionRequest> transactionRequestMono = Mono.just(transactionRequest);

        webTestClient.post()
                .uri("/api/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .body(transactionRequestMono, TransactionRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void createTransaction500ServerError() {

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .accountId(ACCOUNT_ID)
                .operationTypeId(OPERATION_TYPE_ID)
                .amount(AMOUNT)
                .build();
        Mono<TransactionRequest> transactionRequestMono = Mono.just(transactionRequest);

        doThrow(RuntimeException.class).when(transactionService).createTransaction(transactionRequest);

        webTestClient.post()
                .uri("/api/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .body(transactionRequestMono, TransactionRequest.class)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

}
