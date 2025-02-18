package io.pismo.transaction.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long transactionId;

    private Long accountId;

    private Integer operationTypeId;

    private BigDecimal amount;

    private BigDecimal balance;

}
