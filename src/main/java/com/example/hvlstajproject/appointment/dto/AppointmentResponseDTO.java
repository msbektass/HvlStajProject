package com.example.hvlstajproject.appointment.dto;

import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
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
