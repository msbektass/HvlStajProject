package com.example.hvlstajproject.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(Long id) {
        super("Girilen id ye sahip hasta bulunamadı: " + id);
    }
}
