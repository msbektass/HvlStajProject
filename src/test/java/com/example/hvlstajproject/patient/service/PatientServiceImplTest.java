package com.example.hvlstajproject.patient.service;

import com.example.hvlstajproject.common.exception.common.DuplicateTcNoException;
import com.example.hvlstajproject.common.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.manager.PatientManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientServiceImpl;

    @Mock
    private PatientManager patientManager;

    @Test
    void createPatient_shouldReturnResponse_whenRequestIsValid() {
        String tcNo = "12345678901";
        PatientRequestDTO request = new PatientRequestDTO();
        request.setTcNo(tcNo);
        PatientResponseDTO expectedResponse = new PatientResponseDTO();
        expectedResponse.setTcNo(tcNo);

        when(patientManager.existsByTcNo(tcNo)).thenReturn(false);
        when(patientManager.create(request)).thenReturn(expectedResponse);
        PatientResponseDTO actualResponse = patientServiceImpl.createPatient(request);

        assertSame(expectedResponse, actualResponse);
        verify(patientManager).existsByTcNo(tcNo);
        verify(patientManager).create(request);
    }

    @Test
    void createPatient_shouldThrowException_whenTcNoAlreadyExists() {
        String tcNo = "12345678901";
        PatientRequestDTO request = new PatientRequestDTO();
        request.setTcNo(tcNo);

        when(patientManager.existsByTcNo(tcNo)).thenReturn(true);

        assertThrows(DuplicateTcNoException.class, () -> patientServiceImpl.createPatient(request));
        verify(patientManager).existsByTcNo(tcNo);
        verify(patientManager, never()).create(any());
    }

    @Test
    void getPatientById_shouldReturnResponse_whenPatientExists() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);
        PatientResponseDTO expectedResponse = new PatientResponseDTO();
        expectedResponse.setTcNo("12345678901");

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientManager.toResponseDTO(patient)).thenReturn(expectedResponse);
        PatientResponseDTO actualResponse = patientServiceImpl.getPatientById(patientId);

        assertSame(expectedResponse, actualResponse);
        verify(patientManager).findById(patientId);
        verify(patientManager).toResponseDTO(patient);
    }

    @Test
    void getPatientById_shouldThrowException_whenPatientDoesNotExist() {
        Long patientId = 1L;

        when(patientManager.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.getPatientById(patientId));
        verify(patientManager).findById(patientId);
        verify(patientManager, never()).toResponseDTO(any());
    }

    @Test
    void getAllPatients_shouldReturnPatientList() {
        PatientResponseDTO response = new PatientResponseDTO();
        response.setTcNo("12345678901");
        List<PatientResponseDTO> expectedPatients = List.of(response);

        when(patientManager.findAll()).thenReturn(expectedPatients);
        List<PatientResponseDTO> actualPatients = patientServiceImpl.getAllPatients();

        assertSame(expectedPatients, actualPatients);
        verify(patientManager).findAll();
    }

    @Test
    void updatePatient_shouldReturnResponse_whenRequestIsValid() {
        Long patientId = 1L;
        String tcNo = "12345678901";
        Patient patient = new Patient();
        patient.setId(patientId);
        PatientRequestDTO request = new PatientRequestDTO();
        request.setTcNo(tcNo);
        PatientResponseDTO expectedResponse = new PatientResponseDTO();
        expectedResponse.setTcNo(tcNo);

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientManager.existsByTcNoAndIdNot(tcNo, patientId)).thenReturn(false);
        when(patientManager.update(patient, request)).thenReturn(expectedResponse);
        PatientResponseDTO actualResponse = patientServiceImpl.updatePatient(patientId, request);

        assertSame(expectedResponse, actualResponse);
        verify(patientManager).findById(patientId);
        verify(patientManager).existsByTcNoAndIdNot(tcNo, patientId);
        verify(patientManager).update(patient, request);
    }

    @Test
    void updatePatient_shouldThrowException_whenPatientDoesNotExist() {
        Long patientId = 1L;
        PatientRequestDTO request = new PatientRequestDTO();

        when(patientManager.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.updatePatient(patientId, request));
        verify(patientManager).findById(patientId);
        verify(patientManager, never()).update(any(), any());
    }

    @Test
    void updatePatient_shouldThrowException_whenTcNoBelongsToAnotherPatient() {
        Long patientId = 1L;
        String tcNo = "12345678901";
        Patient patient = new Patient();
        patient.setId(patientId);
        PatientRequestDTO request = new PatientRequestDTO();
        request.setTcNo(tcNo);

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientManager.existsByTcNoAndIdNot(tcNo, patientId)).thenReturn(true);

        assertThrows(DuplicateTcNoException.class, () -> patientServiceImpl.updatePatient(patientId, request));
        verify(patientManager).findById(patientId);
        verify(patientManager).existsByTcNoAndIdNot(tcNo, patientId);
        verify(patientManager, never()).update(any(), any());
    }

    @Test
    void deletePatient_shouldDeletePatient_whenPatientExists() {
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setId(patientId);

        when(patientManager.findById(patientId)).thenReturn(Optional.of(patient));
        patientServiceImpl.deletePatient(patientId);

        verify(patientManager).findById(patientId);
        verify(patientManager).delete(patient);
    }

    @Test
    void deletePatient_shouldThrowException_whenPatientDoesNotExist() {
        Long patientId = 1L;

        when(patientManager.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientServiceImpl.deletePatient(patientId));
        verify(patientManager).findById(patientId);
        verify(patientManager, never()).delete(any());
    }
}
