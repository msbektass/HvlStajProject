package com.example.hvlstajproject.doctor.service;

import com.example.hvlstajproject.common.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.manager.DoctorManager;
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
public class DoctorServiceImplTest {

    @InjectMocks
    private DoctorServiceImpl doctorServiceImpl;

    @Mock
    private DoctorManager doctorManager;

    @Test
    void createDoctor_shouldReturnResponse_whenRequestIsValid() {
        String telNo = "+905551112233";
        DoctorRequestDTO request = new DoctorRequestDTO();
        request.setTelNo(telNo);
        DoctorResponseDTO expectedResponse = new DoctorResponseDTO();
        expectedResponse.setFirstName("Ahmet");

        when(doctorManager.existsByTelNo(telNo)).thenReturn(false);
        when(doctorManager.create(request)).thenReturn(expectedResponse);
        DoctorResponseDTO actualResponse = doctorServiceImpl.createDoctor(request);

        assertSame(expectedResponse, actualResponse);
        verify(doctorManager).existsByTelNo(telNo);
        verify(doctorManager).create(request);
    }

    @Test
    void createDoctor_shouldThrowException_whenTelNoAlreadyExists() {
        String telNo = "+905551112233";
        DoctorRequestDTO request = new DoctorRequestDTO();
        request.setTelNo(telNo);

        when(doctorManager.existsByTelNo(telNo)).thenReturn(true);

        assertThrows(DuplicateTelNoException.class, () -> doctorServiceImpl.createDoctor(request));
        verify(doctorManager).existsByTelNo(telNo);
        verify(doctorManager, never()).create(any());
    }

    @Test
    void getDoctorById_shouldReturnResponse_whenDoctorExists() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        DoctorResponseDTO expectedResponse = new DoctorResponseDTO();
        expectedResponse.setFirstName("Ahmet");

        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorManager.toResponseDTO(doctor)).thenReturn(expectedResponse);
        DoctorResponseDTO actualResponse = doctorServiceImpl.getDoctorById(doctorId);

        assertSame(expectedResponse, actualResponse);
        verify(doctorManager).findById(doctorId);
        verify(doctorManager).toResponseDTO(doctor);
    }

    @Test
    void getDoctorById_shouldThrowException_whenDoctorDoesNotExist() {
        Long doctorId = 1L;

        when(doctorManager.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorServiceImpl.getDoctorById(doctorId));
        verify(doctorManager).findById(doctorId);
        verify(doctorManager, never()).toResponseDTO(any());
    }

    @Test
    void getAllDoctors_shouldReturnDoctorList() {
        DoctorResponseDTO response = new DoctorResponseDTO();
        response.setFirstName("Ahmet");
        List<DoctorResponseDTO> expectedDoctors = List.of(response);

        when(doctorManager.findAll()).thenReturn(expectedDoctors);
        List<DoctorResponseDTO> actualDoctors = doctorServiceImpl.getAllDoctors();

        assertSame(expectedDoctors, actualDoctors);
        verify(doctorManager).findAll();
    }

    @Test
    void updateDoctor_shouldReturnResponse_whenRequestIsValid() {
        Long doctorId = 1L;
        String telNo = "+905551112233";
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        DoctorRequestDTO request = new DoctorRequestDTO();
        request.setTelNo(telNo);
        DoctorResponseDTO expectedResponse = new DoctorResponseDTO();
        expectedResponse.setFirstName("Ahmet");

        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorManager.existsByTelNoAndIdNot(telNo, doctorId)).thenReturn(false);
        when(doctorManager.update(doctor, request)).thenReturn(expectedResponse);
        DoctorResponseDTO actualResponse = doctorServiceImpl.updateDoctor(doctorId, request);

        assertSame(expectedResponse, actualResponse);
        verify(doctorManager).findById(doctorId);
        verify(doctorManager).existsByTelNoAndIdNot(telNo, doctorId);
        verify(doctorManager).update(doctor, request);
    }

    @Test
    void updateDoctor_shouldThrowException_whenDoctorDoesNotExist() {
        Long doctorId = 1L;
        DoctorRequestDTO request = new DoctorRequestDTO();

        when(doctorManager.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorServiceImpl.updateDoctor(doctorId, request));
        verify(doctorManager).findById(doctorId);
        verify(doctorManager, never()).update(any(), any());
    }

    @Test
    void updateDoctor_shouldThrowException_whenTelNoBelongsToAnotherDoctor() {
        Long doctorId = 1L;
        String telNo = "+905551112233";
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        DoctorRequestDTO request = new DoctorRequestDTO();
        request.setTelNo(telNo);

        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorManager.existsByTelNoAndIdNot(telNo, doctorId)).thenReturn(true);

        assertThrows(DuplicateTelNoException.class, () -> doctorServiceImpl.updateDoctor(doctorId, request));
        verify(doctorManager).findById(doctorId);
        verify(doctorManager).existsByTelNoAndIdNot(telNo, doctorId);
        verify(doctorManager, never()).update(any(), any());
    }

    @Test
    void deleteDoctor_shouldDeleteDoctor_whenDoctorExists() {
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(doctorManager.findById(doctorId)).thenReturn(Optional.of(doctor));
        doctorServiceImpl.deleteDoctor(doctorId);

        verify(doctorManager).findById(doctorId);
        verify(doctorManager).delete(doctor);
    }

    @Test
    void deleteDoctor_shouldThrowException_whenDoctorDoesNotExist() {
        Long doctorId = 1L;

        when(doctorManager.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorServiceImpl.deleteDoctor(doctorId));
        verify(doctorManager).findById(doctorId);
        verify(doctorManager, never()).delete(any());
    }
}
