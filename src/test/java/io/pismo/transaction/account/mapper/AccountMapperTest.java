package io.pismo.transaction.account.mapper;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountMapperTest {

    static final Long ID = 1L;
    static final String DOCUMENT_NUMBER = "123456789012";

    @Test
    void toAccountSuccess() {

        AccountRequest accountRequest = AccountRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        Account expectedAccount = Account.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        assertEquals(expectedAccount, AccountMapper.toAccount(accountRequest));
    }

    @Test
    void toAccountResponseSuccess() {

        Account account = Account.builder()
                .id(ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();
        AccountResponse expectedAccountResponse = AccountResponse.builder()
                .accountId(ID)
                .documentNumber(DOCUMENT_NUMBER)
                .build();

        assertEquals(expectedAccountResponse, AccountMapper.toAccountResponse(account));
    }

}
