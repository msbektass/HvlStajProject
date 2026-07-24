package com.example.hvlstajproject.service;

import com.example.hvlstajproject.dto.PatientRequestDTO;
import com.example.hvlstajproject.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO getPatientById(Long id);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO);
    void deletePatient(Long id);
}
