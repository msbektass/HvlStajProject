package com.example.hvlstajproject.appointment.service;


import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentStatusChangeDTO;
import com.example.hvlstajproject.appointment.entity.Appointment;
import com.example.hvlstajproject.appointment.manager.AppointmentManager;
import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import com.example.hvlstajproject.common.exception.appointment.*;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.common.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.manager.DoctorManager;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.manager.PatientManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentManager appointmentManager;

    @Mock
    private PatientManager patientManager;

    @Mock
    private DoctorManager doctorManager;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceImpl;

    @Test
    void createAppointment_shouldReturnResponse_whenRequestIsValid(){
        Long patientId = 1L;
        Long doctorId = 2L;
        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10,30));
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        AppointmentResponseDTO expectedResponse = new AppointmentResponseDTO();
        expectedResponse.setId(10L);
        expectedResponse.setAppointmentDate(appointmentRequestDTO.getAppointmentDate());
        expectedResponse.setAppointmentTime(appointmentRequestDTO.getAppointmentTime());
        expectedResponse.setStatus(EAppointmentStatus.BEKLEMEDE);
        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctor(doctorId,appointmentRequestDTO.getAppointmentDate(),appointmentRequestDTO.getAppointmentTime())).thenReturn(false);
        when(appointmentManager.existsForPatient(patientId,appointmentRequestDTO.getAppointmentDate(),appointmentRequestDTO.getAppointmentTime())).thenReturn(false);
        when(appointmentManager.create(appointmentRequestDTO, patient, doctor)).thenReturn(expectedResponse);

        AppointmentResponseDTO actualResponse = appointmentServiceImpl.createAppointment(appointmentRequestDTO);

        assertSame(expectedResponse, actualResponse);
        verify(appointmentManager).create(appointmentRequestDTO, patient, doctor);
    }

    @Test
    void createAppointment_shouldThrowException_whenDateTimeIsPast(){
        AppointmentRequestDTO pastRequest = new AppointmentRequestDTO(1L, 2L, LocalDate.now().minusDays(1), LocalTime.of(10, 30));
        assertThrows(PastAppointmentException.class, () -> appointmentServiceImpl.createAppointment(pastRequest));
        verifyNoInteractions(patientManager, doctorManager, appointmentManager);
    }

    @Test
    void createAppointment_shouldThrowException_whenPatientDoesNotExist(){
        Long patientId = 1L;
        Long doctorId = 2L;
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(patientManager.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> appointmentServiceImpl.createAppointment(validRequest));
        verify(patientManager).findById(patientId);
        verifyNoInteractions(doctorManager, appointmentManager);
    }

    @Test
    void createAppointment_shouldThrowException_whenDoctorDoesNotExist(){
        Long patientId = 1L;
        Long doctorId = 2L;
        Patient patient = new Patient();
        patient.setId(patientId);
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> appointmentServiceImpl.createAppointment(validRequest));
        verify(patientManager).findById(patientId);
        verify(doctorManager).findById(doctorId);
        verifyNoInteractions(appointmentManager);
    }

    @Test
    void createAppointment_shouldThrowException_whenDoctorHasConflict(){
        Long patientId = 1L;
        Long doctorId = 2L;
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctor(doctorId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime())).thenReturn(true);

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.createAppointment(validRequest));
        verify(patientManager).findById(patientId);
        verify(doctorManager).findById(doctorId);
        verify(appointmentManager, never()).create(any(), any(), any());
    }

    @Test
    void createAppointment_shouldThrowException_whenPatientHasConflict(){
        Long patientId = 1L;
        Long doctorId = 2L;
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctor(doctorId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime())).thenReturn(false);
        when(appointmentManager.existsForPatient(patientId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime())).thenReturn(true);

        assertThrows(PatientAppointmentConflictException.class, () -> appointmentServiceImpl.createAppointment(validRequest));
        verify(patientManager).findById(patientId);
        verify(doctorManager).findById(doctorId);
        verify(appointmentManager, never()).create(any(), any(), any());
    }

    @Test
    void getAppointmentById_shouldReturnResponse_whenAppointmentExists(){
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        AppointmentResponseDTO validResponse = new AppointmentResponseDTO();
        validResponse.setId(appointmentId);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentManager.toResponseDTO(appointment)).thenReturn(validResponse);
        AppointmentResponseDTO actualResponse = appointmentServiceImpl.getAppointmentById(appointmentId);

        assertSame(validResponse, actualResponse);
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager).toResponseDTO(appointment);
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void getAppointmentById_shouldThrowException_whenAppointmentDoesNotExist(){
        Long appointmentId = 1L;

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentServiceImpl.getAppointmentById(appointmentId));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).toResponseDTO(any());
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void getAllAppointments_shouldReturnAppointmentList(){
        AppointmentResponseDTO validResponse = new AppointmentResponseDTO();
        validResponse.setId(1L);
        List<AppointmentResponseDTO> appointmentList = List.of(validResponse);

        when(appointmentManager.findAll()).thenReturn(appointmentList);
        List<AppointmentResponseDTO> actualList = appointmentServiceImpl.getAllAppointments();

        assertSame(appointmentList, actualList);
        verify(appointmentManager).findAll();
        verifyNoInteractions(patientManager, doctorManager);
    }
    @Test
    void updateAppointment_shouldReturnResponse_whenRequestIsValid(){
        Long appointmentId = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.BEKLEMEDE);
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));
        AppointmentResponseDTO validResponse = new AppointmentResponseDTO();

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctorExcludingAppointment(doctorId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime(),appointmentId)).thenReturn(false);
        when(appointmentManager.existsForPatientExcludingAppointment(patientId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime(), appointmentId)).thenReturn(false);
        when(appointmentManager.update(appointment,validRequest,patient,doctor)).thenReturn(validResponse);

        AppointmentResponseDTO actualResponse = appointmentServiceImpl.updateAppointment(appointmentId, validRequest);
        assertSame(validResponse, actualResponse);
        verify(appointmentManager).findById(appointmentId);
        verify(patientManager).findById(patientId);
        verify(doctorManager).findById(doctorId);
        verify(appointmentManager).existsForDoctorExcludingAppointment(doctorId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime(),appointmentId);
        verify(appointmentManager).existsForPatientExcludingAppointment(patientId,validRequest.getAppointmentDate(),validRequest.getAppointmentTime(), appointmentId);
        verify(appointmentManager).update(appointment,validRequest,patient,doctor);
    }

    @Test
    void updateAppointment_shouldThrowException_whenAppointmentDoesNotExist(){
        Long appointmentId = 1L;
        AppointmentRequestDTO validRequest = new AppointmentRequestDTO();

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentServiceImpl.updateAppointment(appointmentId, validRequest) );
        verify(appointmentManager).findById(appointmentId);
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void updateAppointment_shouldThrowException_whenAppointmentIsNotModifiable() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.TAMAMLANMIŞ);
        AppointmentRequestDTO request = new AppointmentRequestDTO();

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));

        assertThrows(AppointmentNotModifiableException.class, () -> appointmentServiceImpl.updateAppointment(appointmentId, request));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).update(any(), any(), any(), any());
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void updateAppointment_shouldThrowException_whenDoctorHasConflict() {
        Long appointmentId = 1L;
        Long patientId = 2L;
        Long doctorId = 3L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.BEKLEMEDE);
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        AppointmentRequestDTO request = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctorExcludingAppointment(doctorId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId)).thenReturn(true);
        when(appointmentManager.existsForPatientExcludingAppointment(patientId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId)).thenReturn(false);

        assertThrows(DoctorAppointmentConflictException.class, () -> appointmentServiceImpl.updateAppointment(appointmentId, request));
        verify(appointmentManager).existsForDoctorExcludingAppointment(doctorId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId);
        verify(appointmentManager).existsForPatientExcludingAppointment(patientId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId);
        verify(appointmentManager, never()).update(any(), any(), any(), any());
    }

    @Test
    void updateAppointment_shouldThrowException_whenPatientHasConflict() {
        Long appointmentId = 1L;
        Long patientId = 2L;
        Long doctorId = 3L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.BEKLEMEDE);
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        AppointmentRequestDTO request = new AppointmentRequestDTO(patientId, doctorId, LocalDate.now().plusDays(1), LocalTime.of(10, 30));

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentManager.existsForDoctorExcludingAppointment(doctorId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId)).thenReturn(false);
        when(appointmentManager.existsForPatientExcludingAppointment(patientId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId)).thenReturn(true);

        assertThrows(PatientAppointmentConflictException.class, () -> appointmentServiceImpl.updateAppointment(appointmentId, request));
        verify(appointmentManager).existsForDoctorExcludingAppointment(doctorId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId);
        verify(appointmentManager).existsForPatientExcludingAppointment(patientId, request.getAppointmentDate(), request.getAppointmentTime(), appointmentId);
        verify(appointmentManager, never()).update(any(), any(), any(), any());
    }

    @Test
    void changeAppointmentStatus_shouldReturnResponse_whenRequestIsValid() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.BEKLEMEDE);
        AppointmentStatusChangeDTO statusChangeDTO = new AppointmentStatusChangeDTO(EAppointmentStatus.ONAYLANDI);
        AppointmentResponseDTO expectedResponse = new AppointmentResponseDTO();
        expectedResponse.setId(appointmentId);
        expectedResponse.setStatus(EAppointmentStatus.ONAYLANDI);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentManager.changeStatus(appointment, EAppointmentStatus.ONAYLANDI)).thenReturn(expectedResponse);
        AppointmentResponseDTO actualResponse = appointmentServiceImpl.changeAppointmentStatus(appointmentId, statusChangeDTO);

        assertSame(expectedResponse, actualResponse);
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager).changeStatus(appointment, EAppointmentStatus.ONAYLANDI);
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void changeAppointmentStatus_shouldThrowException_whenAppointmentDoesNotExist() {
        Long appointmentId = 1L;
        AppointmentStatusChangeDTO statusChangeDTO = new AppointmentStatusChangeDTO(EAppointmentStatus.ONAYLANDI);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentServiceImpl.changeAppointmentStatus(appointmentId, statusChangeDTO));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).changeStatus(any(), any());
        verifyNoInteractions(patientManager, doctorManager);
    }
    @Test
    void changeAppointmentStatus_shouldThrowException_whenAppointmentIsNotModifiable() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.İPTAL);
        AppointmentStatusChangeDTO statusChangeDTO = new AppointmentStatusChangeDTO(EAppointmentStatus.ONAYLANDI);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));

        assertThrows(AppointmentNotModifiableException.class, () -> appointmentServiceImpl.changeAppointmentStatus(appointmentId, statusChangeDTO));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).changeStatus(any(), any());
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void deleteAppointment_shouldDeleteAppointment_whenAppointmentIsModifiable() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.BEKLEMEDE);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));
        appointmentServiceImpl.deleteAppointment(appointmentId);

        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager).delete(appointment);
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void deleteAppointment_shouldThrowException_whenAppointmentDoesNotExist() {
        Long appointmentId = 1L;

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentServiceImpl.deleteAppointment(appointmentId));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).delete(any());
        verifyNoInteractions(patientManager, doctorManager);
    }

    @Test
    void deleteAppointment_shouldThrowException_whenAppointmentIsNotModifiable() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(EAppointmentStatus.TAMAMLANMIŞ);

        when(appointmentManager.findById(appointmentId)).thenReturn(Optional.of(appointment));

        assertThrows(AppointmentNotModifiableException.class, () -> appointmentServiceImpl.deleteAppointment(appointmentId));
        verify(appointmentManager).findById(appointmentId);
        verify(appointmentManager, never()).delete(any());
        verifyNoInteractions(patientManager, doctorManager);
    }
}
