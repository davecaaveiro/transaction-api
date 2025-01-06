package io.pismo.transaction.transaction.repository;

import io.pismo.transaction.transaction.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends R2dbcRepository<Transaction, Long> {

    @Query("SELECT * FROM transaction WHERE operation_type_id = 1 AND balance < 0 ORDER BY event_date")
    Flux<Transaction> findAllTransactionsWithNegativeBalanceOrderByEventDate();
}
