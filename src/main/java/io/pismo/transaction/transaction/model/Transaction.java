package io.pismo.transaction.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private Long id;

    private Long accountId;

    private Integer operationTypeId;

    private BigDecimal amount;

    private BigDecimal balance;

    @CreatedDate
    private LocalDateTime eventDate;

    @Version
    private Long version;

}
