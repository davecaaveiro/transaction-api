package io.pismo.transaction.account.dto;

import io.pismo.transaction.account.validator.DocumentNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @DocumentNumber
    private String documentNumber;

}
