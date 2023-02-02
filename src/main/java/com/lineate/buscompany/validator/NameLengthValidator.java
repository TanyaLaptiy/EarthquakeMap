package com.lineate.buscompany.validator;


import com.lineate.buscompany.validator.annotations.NameLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameLengthValidator implements ConstraintValidator<NameLength, String> {

    @Override
    public void initialize(NameLength constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= 5 && s.length() <= 20;
    }
}
