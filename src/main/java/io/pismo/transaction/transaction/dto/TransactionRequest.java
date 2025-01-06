package io.pismo.transaction.transaction.dto;

import io.pismo.transaction.transaction.validator.AccountId;
import io.pismo.transaction.transaction.validator.OperationTypeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @AccountId
    private Long accountId;

    @OperationTypeId
    private Integer operationTypeId;

    private BigDecimal amount;

}
