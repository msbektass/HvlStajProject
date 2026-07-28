package com.example.hvlstajproject.common.exception.common;

public class DuplicateTelNoException extends RuntimeException{
    public DuplicateTelNoException(String telNo) {
        super("Girilen telefon numarasına sahip kullanıcı zaten sistemde kayıtlı: " + telNo);
    }
}
