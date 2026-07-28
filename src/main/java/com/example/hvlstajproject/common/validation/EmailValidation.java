package com.example.hvlstajproject.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EmailValidator.class})
public @interface EmailValidation {
    public String message() default "Geçersiz e-posta formatı";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
