package com.example.hvlstajproject.patient.service;

import com.example.hvlstajproject.common.exception.common.DuplicateTcNoException;
import com.example.hvlstajproject.common.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.manager.PatientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientManager patientManager;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        checkDuplicateTcNo(requestDTO.getTcNo(), null);
        return patientManager.create(requestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = getPatientOrThrow(id);
        return patientManager.toResponseDTO(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getAllPatients() {
        return patientManager.findAll();
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO) {
        Patient patient = getPatientOrThrow(id);
        checkDuplicateTcNo(requestDTO.getTcNo(), id);
        return patientManager.update(patient, requestDTO);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = getPatientOrThrow(id);
        patientManager.delete(patient);
    }

    private Patient getPatientOrThrow(Long id) {
        return patientManager.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    private void checkDuplicateTcNo(String tcNo, Long excludedPatientId) {
        boolean duplicate;
        if (excludedPatientId == null) {
            duplicate = patientManager.existsByTcNo(tcNo);
        } else {
            duplicate = patientManager.existsByTcNoAndIdNot(tcNo, excludedPatientId);
        }
        if (duplicate) {
            throw new DuplicateTcNoException(tcNo);
        }
    }
}
