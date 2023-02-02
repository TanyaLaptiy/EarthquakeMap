package com.lineate.buscompany.validator.annotations;

import com.lineate.buscompany.validator.PhoneLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneLengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneLength {
    String message() default "INVALID_LENGTH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
