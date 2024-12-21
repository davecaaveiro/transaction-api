package io.pismo.transaction.account.service;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.mapper.AccountMapper;
import io.pismo.transaction.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Mono<AccountResponse> createAccount(AccountRequest accountRequest) {

        return accountRepository.save(AccountMapper.toAccount(accountRequest))
                .map(AccountMapper::toAccountResponse);
    }

    public Mono<AccountResponse> getAccount(Long accountId) {

        return accountRepository.findById(accountId).map(AccountMapper::toAccountResponse);
    }

}