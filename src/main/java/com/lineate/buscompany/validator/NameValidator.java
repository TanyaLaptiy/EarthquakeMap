package com.lineate.buscompany.validator;

import com.lineate.buscompany.validator.annotations.Name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {

    @Override
    public void initialize(Name constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
