package com.example.hvlstajproject.appointment.controller;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentStatusChangeDTO;
import com.example.hvlstajproject.appointment.service.AppointmentService;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Randevu işlemleri")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Bütün randevuları listeleme")
    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Operation(summary = "Randevu üzerinden hasta çekme")
    @GetMapping("/{appointmentId}/patient")
    public PatientResponseDTO getPatientFromAppointment(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId).getPatient();
    }

    @Operation(summary = "Randevu üzerinden doktor çekme")
    @GetMapping("/{appointmentId}/doctor")
    public DoctorResponseDTO getDoctorFromAppointment(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId).getDoctor();
    }

    @Operation(summary = "Yeni randevu oluşturma")
    @PostMapping
    public AppointmentResponseDTO createAppointment(@Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.createAppointment(requestDTO);
    }

    @Operation(summary = "Id ile randevu çekme")
    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @Operation(summary = "Randevu güncelleme")
    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.updateAppointment(id, requestDTO);
    }

    @Operation(summary = "Randevu durumunu güncelleme")
    @PatchMapping("/{id}/status")
    public AppointmentResponseDTO changeAppointmentStatus(@PathVariable Long id, @Valid @RequestBody AppointmentStatusChangeDTO statusChangeDTO) {
        return appointmentService.changeAppointmentStatus(id, statusChangeDTO);
    }

    @Operation(summary = "Randevu silme")
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
