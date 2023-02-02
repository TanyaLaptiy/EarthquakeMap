package com.lineate.buscompany.validator;


import com.lineate.buscompany.validator.annotations.PhoneLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneLengthValidator implements ConstraintValidator<PhoneLength, String> {
    @Override
    public void initialize(PhoneLength constraintAnnotation) {
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.length() >= 1 && s.length() <= 9;
    }
}
