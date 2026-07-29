package com.example.hvlstajproject.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TcNoValidator.class})
public @interface TcNoValidation {
    public String message() default "Girdiğiniz veri geçersiz";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
