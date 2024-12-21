package io.pismo.transaction.transaction.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class AccountIdValidator implements ConstraintValidator<AccountId, Long> {

    @Override
    public boolean isValid(Long accountId, ConstraintValidatorContext context) {

        return ObjectUtils.isNotEmpty(accountId);
    }
}