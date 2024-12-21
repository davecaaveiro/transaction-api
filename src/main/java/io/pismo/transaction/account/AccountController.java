package io.pismo.transaction.account;

import io.pismo.transaction.account.dto.AccountRequest;
import io.pismo.transaction.account.dto.AccountResponse;
import io.pismo.transaction.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    private final AccountService accountService;

    @PostMapping
    public Mono<ResponseEntity<AccountResponse>> createAccount(@Validated @RequestBody AccountRequest accountRequest) {

        logger.info("Creating account with document number: {}", accountRequest.getDocumentNumber());

        return accountService.createAccount(accountRequest).map(accountResponse ->
                ResponseEntity.created(URI.create("/api/accounts/" + accountResponse.getAccountId())).body(accountResponse)
        );
    }

    @GetMapping("/{accountId}")
    public Mono<ResponseEntity<AccountResponse>> getAccount(@PathVariable("accountId") Long accountId) {

        logger.info("Getting account with id: {}", accountId);

        return accountService.getAccount(accountId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
