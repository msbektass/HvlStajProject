package com.example.hvlstajproject.exception;

public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(Long id) {
        super("Girilen id ye sahip doktor bulunamadı: " + id);
    }
}
