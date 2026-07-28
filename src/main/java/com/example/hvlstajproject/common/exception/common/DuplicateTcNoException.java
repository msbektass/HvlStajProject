package com.example.hvlstajproject.common.exception.common;

public class DuplicateTcNoException extends RuntimeException {
    public DuplicateTcNoException(String tcNo) {
        super("Girilen kimlik numarasına sahip hasta zaten sistemde kayıtlı: " + tcNo);
    }
}
