package com.example.hvlstajproject.service;

import com.example.hvlstajproject.dto.DtoPatientRequest;
import com.example.hvlstajproject.dto.DtoPatientResponse;

import java.util.List;

public interface PatientService {
    DtoPatientResponse createPatient(DtoPatientRequest dtoPatientRequest);
    DtoPatientResponse getPatientById(Long id);
    List<DtoPatientResponse> getAllPatients();
    DtoPatientResponse updatePatient(Long id, DtoPatientRequest dtoPatientRequest);
    void deletePatient(Long id);
}
