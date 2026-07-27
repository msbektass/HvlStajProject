package com.example.hvlstajproject.patient.service;

import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO getPatientById(Long id);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO);
    void deletePatient(Long id);
}
