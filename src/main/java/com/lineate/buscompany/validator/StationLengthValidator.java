package com.lineate.buscompany.validator;


import com.lineate.buscompany.validator.annotations.StationLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StationLengthValidator implements ConstraintValidator<StationLength, String> {

    @Override
    public void initialize(StationLength constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.length() >= 3 && s.length() <= 15;
    }
}
