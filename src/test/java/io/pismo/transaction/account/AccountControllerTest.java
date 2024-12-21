package io.pismo.transaction.account;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.service.AccountService;
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
class AccountControllerTest {

    static final Long ID = 1L;
    static final String DOCUMENT_NUMBER = "123456789012";

    @Autowired
    WebTestClient webTestClient;

    @MockitoSpyBean
    AccountService accountService;

    @Test
    void createAccount201Created() {

        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        Mono<AccountRequest> accountRequestMono = Mono.just(accountRequest);
        Mono<AccountResponse> accountResponseMono = Mono.just(AccountResponse.builder()
                .accountId(ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build());

        doAnswer(invocation -> accountResponseMono).when(accountService).createAccount(accountRequest);

        webTestClient.post()
                .uri("/api/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .body(accountRequestMono, AccountRequest.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.accountId")
                .isEqualTo(ID)
                .jsonPath("$.documentNumber")
                .isEqualTo(DOCUMENT_NUMBER);
    }

    @Test
    void createAccount400BadRequest() {

        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(null)
                .build();
        Mono<AccountRequest> accountRequestMono = Mono.just(accountRequest);

        webTestClient.post()
                .uri("/api/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .body(accountRequestMono, AccountRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void createAccount500ServerError() {

        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        Mono<AccountRequest> accountRequestMono = Mono.just(accountRequest);

        doThrow(RuntimeException.class).when(accountService).createAccount(accountRequest);

        webTestClient.post()
                .uri("/api/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .body(accountRequestMono, AccountRequest.class)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    void getAccount200Ok() {

        Mono<AccountResponse> accountResponseMono = Mono.just(
                AccountResponse.builder()
                        .accountId(ID)
                        .documentNumber(DOCUMENT_NUMBER)
                        .build()
        );

        doAnswer(invocation -> accountResponseMono).when(accountService).getAccount(ID);

        webTestClient.get()
                .uri("/api/accounts/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.accountId")
                .isEqualTo(ID)
                .jsonPath("$.documentNumber")
                .isEqualTo(DOCUMENT_NUMBER);
    }

    @Test
    void getAccount400BadRequest() {

        webTestClient.get()
                .uri("/api/accounts/*")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void getAccount404NotFound() {

        doAnswer(invocation -> Mono.empty()).when(accountService).getAccount(ID);

        webTestClient.get()
                .uri("/api/accounts/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void getAccount500ServerError() {

        doThrow(RuntimeException.class).when(accountService).getAccount(ID);

        webTestClient.get()
                .uri("/api/accounts/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }
}
