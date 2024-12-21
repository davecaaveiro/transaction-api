package io.pismo.transaction.transaction.repository;

import io.pismo.transaction.transaction.model.OperationType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends R2dbcRepository<OperationType, Integer> {
}
