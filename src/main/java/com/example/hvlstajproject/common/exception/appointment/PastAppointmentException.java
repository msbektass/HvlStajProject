package com.example.hvlstajproject.common.exception.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class PastAppointmentException extends RuntimeException {

    public PastAppointmentException(LocalDate appointmentDate, LocalTime appointmentTime) {
        super("Geçmiş tarih ve saate randevu oluşturulamaz: " + appointmentDate + " " + appointmentTime);
    }
}