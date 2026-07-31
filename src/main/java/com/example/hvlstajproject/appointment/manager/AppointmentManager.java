package com.example.hvlstajproject.appointment.manager;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.entity.Appointment;
import com.example.hvlstajproject.appointment.mapper.AppointmentMapper;
import com.example.hvlstajproject.appointment.repository.AppointmentRepository;
import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.patient.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppointmentManager {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentResponseDTO create(AppointmentRequestDTO requestDTO, Patient patient, Doctor doctor) {
        Appointment appointment = appointmentMapper.toAppointment(requestDTO);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDTO(savedAppointment);
    }

    public AppointmentResponseDTO update(Appointment appointment, AppointmentRequestDTO requestDTO, Patient patient, Doctor doctor) {
        appointmentMapper.updateAppointment(requestDTO, appointment);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDTO(updatedAppointment);
    }

    public AppointmentResponseDTO changeStatus(Appointment appointment, EAppointmentStatus status) {
        appointment.setStatus(status);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDTO(updatedAppointment);
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<AppointmentResponseDTO> findAll() {
        return appointmentRepository.findAllWithPatientAndDoctor().stream().map(appointmentMapper::toResponseDTO).toList();
    }

    public AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return appointmentMapper.toResponseDTO(appointment);
    }

    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    public boolean existsForDoctor(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        return appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTime(doctorId, appointmentDate, appointmentTime);
    }

    public boolean existsForPatient(Long patientId, LocalDate appointmentDate, LocalTime appointmentTime) {
        return appointmentRepository.existsByPatient_IdAndAppointmentDateAndAppointmentTime(patientId, appointmentDate, appointmentTime);
    }

    public boolean existsForDoctorExcludingAppointment(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime, Long excludedAppointmentId) {
        return appointmentRepository.existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndIdNot(doctorId, appointmentDate, appointmentTime, excludedAppointmentId);
    }

    public boolean existsForPatientExcludingAppointment(Long patientId, LocalDate appointmentDate, LocalTime appointmentTime, Long excludedAppointmentId) {
        return appointmentRepository.existsByPatient_IdAndAppointmentDateAndAppointmentTimeAndIdNot(patientId, appointmentDate, appointmentTime, excludedAppointmentId);
    }
}
