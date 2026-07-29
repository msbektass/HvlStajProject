package com.example.hvlstajproject.common.exception.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorAppointmentConflictException extends RuntimeException {

    public DoctorAppointmentConflictException(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        super("Doktorun belirtilen tarih ve saatte başka bir randevusu bulunmaktadır. " + "Doktor ID: " + doctorId + ", tarih: " + appointmentDate + ", saat: " + appointmentTime);
    }
}