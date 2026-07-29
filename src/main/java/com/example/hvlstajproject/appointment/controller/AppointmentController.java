package com.example.hvlstajproject.appointment.controller;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentStatusChangeDTO;
import com.example.hvlstajproject.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping
    public AppointmentResponseDTO createAppointment(@Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.createAppointment(requestDTO);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.updateAppointment(id, requestDTO);
    }

    @PatchMapping("/{id}/status")
    public AppointmentResponseDTO changeAppointmentStatus(@PathVariable Long id, @Valid @RequestBody AppointmentStatusChangeDTO statusChangeDTO) {
        return appointmentService.changeAppointmentStatus(id, statusChangeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
