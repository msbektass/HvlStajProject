package com.example.hvlstajproject.common.exception.appointment;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super("Girilen id'ye sahip randevu bulunamadı: " + id);
    }
}
