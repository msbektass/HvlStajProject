package com.example.hvlstajproject.common.exception.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class PatientAppointmentConflictException extends RuntimeException {

    public PatientAppointmentConflictException(Long patientId, LocalDate appointmentDate, LocalTime appointmentTime) {
        super("Hastanın belirtilen tarih ve saatte başka bir randevusu bulunmaktadır. " + "Hasta ID: " + patientId + ", tarih: " + appointmentDate + ", saat: " + appointmentTime);
    }
}