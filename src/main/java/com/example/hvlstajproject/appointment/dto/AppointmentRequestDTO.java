package com.example.hvlstajproject.appointment.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @NotNull(message = "Hasta ID bilgisi boş bırakılamaz")
    private Long patientId;

    @NotNull(message = "Doktor ID bilgisi boş bırakılamaz")
    private Long doctorId;

    @NotNull(message = "Randevu tarihi boş bırakılamaz")
    @FutureOrPresent(message = "Randevu tarihi geçmiş bir tarih olamaz")
    private LocalDate appointmentDate;

    @NotNull(message = "Randevu saati boş bırakılamaz")
    private LocalTime appointmentTime;
}
