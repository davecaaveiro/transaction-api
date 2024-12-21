package io.pismo.transaction.transaction.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class OperationTypeIdValidator implements ConstraintValidator<OperationTypeId, Integer> {

    @Override
    public boolean isValid(Integer operationTypeId, ConstraintValidatorContext context) {

        return ObjectUtils.isNotEmpty(operationTypeId);
    }
}