package com.example.hvlstajproject.appointment.dto;

import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private Long id;
    private PatientResponseDTO patient;
    private DoctorResponseDTO doctor;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private EAppointmentStatus status;

}
