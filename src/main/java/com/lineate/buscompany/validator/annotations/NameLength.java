package com.lineate.buscompany.validator.annotations;

import com.lineate.buscompany.validator.NameLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameLengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameLength {
    String message() default "INVALID_LENGTH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
