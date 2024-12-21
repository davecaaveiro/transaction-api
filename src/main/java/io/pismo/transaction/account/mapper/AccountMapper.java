package io.pismo.transaction.account.mapper;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.model.Account;

public class AccountMapper {

    public static Account toAccount(AccountRequest accountRequest) {

        return Account.builder()
                .documentNumber(accountRequest.getDocumentNumber())
                .build();
    }

    public static AccountResponse toAccountResponse(Account account) {

        return AccountResponse.builder()
                .accountId(account.getId())
                .documentNumber(account.getDocumentNumber())
                .build();
    }

}
