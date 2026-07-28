package com.example.hvlstajproject.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TcNoValidator implements ConstraintValidator<TcNoValidation, String> {
    private static final Pattern TcPattern = Pattern.compile("^[0-9]{11}$");
    @Override
    public boolean isValid(String tcNo, ConstraintValidatorContext context) {
        if(tcNo == null || tcNo.isEmpty()){
            return false;
        }
        else if (tcNo.charAt(0) == '0'){
            return false;
        }
        else if (!TcPattern.matcher(tcNo).matches()){
            return false;
        }
        int odd = ((tcNo.charAt(0) - '0') + (tcNo.charAt(2) - '0') + (tcNo.charAt(4) - '0') + (tcNo.charAt(6) - '0') + (tcNo.charAt(8) - '0')) * 7;
        int even = (tcNo.charAt(1) - '0') + (tcNo.charAt(3) - '0') + (tcNo.charAt(5) - '0') + (tcNo.charAt(7) - '0');
        if ((tcNo.charAt(9) - '0') != ((odd - even) % 10)){
            return false;
        }
        if((tcNo.charAt(10) - '0') != (((odd / 7) + even + (tcNo.charAt(0) - '0')) % 10)){
            return false;
        }
        return true;
    }
}
