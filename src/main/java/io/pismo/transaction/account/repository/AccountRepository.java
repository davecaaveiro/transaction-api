package io.pismo.transaction.account.repository;

import io.pismo.transaction.account.model.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends R2dbcRepository<Account, Long> {
}
