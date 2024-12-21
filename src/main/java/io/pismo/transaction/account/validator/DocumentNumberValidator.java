package io.pismo.transaction.account.validator;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentNumberValidator implements ConstraintValidator<DocumentNumber, String> {

    @Override
    public boolean isValid(String documentNumber, ConstraintValidatorContext context) {

        return StringUtils.isNotBlank(documentNumber);
    }
}