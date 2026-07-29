package com.example.hvlstajproject.appointment.service;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentStatusChangeDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO);

    AppointmentResponseDTO getAppointmentById(Long id);

    List<AppointmentResponseDTO> getAllAppointments();

    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO);

    AppointmentResponseDTO changeAppointmentStatus(Long id, AppointmentStatusChangeDTO statusChangeDTO);

    void deleteAppointment(Long id);
}