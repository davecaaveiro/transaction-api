package io.pismo.transaction.account.service;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.model.Account;
import io.pismo.transaction.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class AccountServiceTest {

    static final Long ID = 1L;
    static final String DOCUMENT_NUMBER = "123456789012";

    @Autowired
    AccountService accountService;

    @MockitoSpyBean
    AccountRepository accountRepository;

    @Test
    void createAccountSuccess() {

        Account account = Account.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        AccountResponse expectedAccountResponse = AccountResponse.builder()
                .accountId(ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        doAnswer(invocation -> Mono.just(expectedAccountResponse)).when(accountRepository).save(account);

        accountService.createAccount(accountRequest).flatMap(accountResponse -> {
            assertEquals(expectedAccountResponse, accountResponse);
            return Mono.just(accountResponse);
        });
    }

    @Test
    void getAccountSuccess() {

        AccountResponse expectedAccountResponse = AccountResponse.builder()
                .accountId(ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        doAnswer(invocation -> Mono.just(expectedAccountResponse)).when(accountRepository).findById(ID);

        accountService.getAccount(ID).flatMap(accountResponse -> {
            assertEquals(expectedAccountResponse, accountResponse);
            return Mono.just(accountResponse);
        });
    }

    @Test
    void createAccountFailure() {

        Account account = Account.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        doThrow(RuntimeException.class).when(accountRepository).save(account);

        assertThrows(RuntimeException.class, () -> accountService.createAccount(accountRequest));
    }

    @Test
    void getAccountFailure() {

        doThrow(RuntimeException.class).when(accountRepository).findById(ID);

        assertThrows(RuntimeException.class, () -> accountService.getAccount(ID));
    }

}
