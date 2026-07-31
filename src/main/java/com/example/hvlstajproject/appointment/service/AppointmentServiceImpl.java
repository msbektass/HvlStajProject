package com.example.hvlstajproject.appointment.service;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentStatusChangeDTO;
import com.example.hvlstajproject.appointment.entity.Appointment;
import com.example.hvlstajproject.appointment.manager.AppointmentManager;
import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import com.example.hvlstajproject.common.exception.appointment.AppointmentNotFoundException;
import com.example.hvlstajproject.common.exception.appointment.AppointmentNotModifiableException;
import com.example.hvlstajproject.common.exception.appointment.DoctorAppointmentConflictException;
import com.example.hvlstajproject.common.exception.appointment.PastAppointmentException;
import com.example.hvlstajproject.common.exception.appointment.PatientAppointmentConflictException;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.common.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.manager.DoctorManager;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.manager.PatientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentManager appointmentManager;
    private final PatientManager patientManager;
    private final DoctorManager doctorManager;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO) {
        checkAppointmentDateTime(requestDTO);
        Patient patient = getPatientOrThrow(requestDTO.getPatientId());
        Doctor doctor = getDoctorOrThrow(requestDTO.getDoctorId());
        checkAppointmentConflicts(requestDTO, null);
        return appointmentManager.create(requestDTO, patient, doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = getAppointmentOrThrow(id);
        return appointmentManager.toResponseDTO(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentManager.findAll();
    }

    @Override
    @Transactional
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO requestDTO) {
        Appointment appointment = getAppointmentOrThrow(id);
        checkAppointmentModifiable(appointment);
        checkAppointmentDateTime(requestDTO);
        Patient patient = getPatientOrThrow(requestDTO.getPatientId());
        Doctor doctor = getDoctorOrThrow(requestDTO.getDoctorId());
        checkAppointmentConflicts(requestDTO, id);
        return appointmentManager.update(appointment, requestDTO, patient, doctor);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO changeAppointmentStatus(Long id, AppointmentStatusChangeDTO statusChangeDTO) {
        Appointment appointment = getAppointmentOrThrow(id);
        checkAppointmentModifiable(appointment);
        return appointmentManager.changeStatus(appointment, statusChangeDTO.getStatus());
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentOrThrow(id);
        checkAppointmentModifiable(appointment);
        appointmentManager.delete(appointment);
    }

    private Appointment getAppointmentOrThrow(Long id) {
        return appointmentManager.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    private Patient getPatientOrThrow(Long patientId) {
        return patientManager.findById(patientId).orElseThrow(() -> new PatientNotFoundException(patientId));
    }

    private Doctor getDoctorOrThrow(Long doctorId) {
        return doctorManager.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
    }

    private void checkAppointmentDateTime(AppointmentRequestDTO requestDTO) {
        LocalDateTime appointmentDateTime = LocalDateTime.of(requestDTO.getAppointmentDate(), requestDTO.getAppointmentTime());
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new PastAppointmentException(requestDTO.getAppointmentDate(), requestDTO.getAppointmentTime());
        }
    }

    private void checkAppointmentConflicts(AppointmentRequestDTO dto, Long excludedAppointmentId
    ) {
        Long doctorId = dto.getDoctorId();
        Long patientId = dto.getPatientId();
        LocalDate date = dto.getAppointmentDate();
        LocalTime time = dto.getAppointmentTime();
        boolean doctorConflict = excludedAppointmentId == null ? appointmentManager.existsForDoctor(doctorId, date, time) : appointmentManager.existsForDoctorExcludingAppointment(doctorId, date, time, excludedAppointmentId);

        if (doctorConflict) {
            throw new DoctorAppointmentConflictException(doctorId, date, time);
        }

        boolean patientConflict = excludedAppointmentId == null ? appointmentManager.existsForPatient(patientId, date, time) : appointmentManager.existsForPatientExcludingAppointment(patientId, date, time, excludedAppointmentId);

        if (patientConflict) {
            throw new PatientAppointmentConflictException(patientId, date, time);
        }
    }

    private void checkAppointmentModifiable(Appointment appointment) {
        if (appointment.getStatus() == EAppointmentStatus.TAMAMLANMIŞ || appointment.getStatus() == EAppointmentStatus.İPTAL) {
            throw new AppointmentNotModifiableException(appointment.getId(), appointment.getStatus());
        }
    }
}