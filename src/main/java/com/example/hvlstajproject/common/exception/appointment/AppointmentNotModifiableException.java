package com.example.hvlstajproject.common.exception.appointment;

import com.example.hvlstajproject.common.enums.EAppointmentStatus;

public class AppointmentNotModifiableException extends RuntimeException {

    public AppointmentNotModifiableException(Long appointmentId, EAppointmentStatus status) {
        super("Randevu mevcut durumu nedeniyle değiştirilemez. " + "Randevu ID: " + appointmentId + ", durum: " + status);
    }
}