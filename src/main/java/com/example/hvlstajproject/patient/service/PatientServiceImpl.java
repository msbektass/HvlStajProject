package com.example.hvlstajproject.patient.service;

import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.manager.PatientManager;
import com.example.hvlstajproject.patient.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientManager patientManager;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientMapper.toPatient(patientRequestDTO);
        Patient saved = patientManager.save(patient);
        return patientMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientManager.getById(id);
        return patientMapper.toResponseDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getAllPatients() {
        return patientManager.getAll().stream().map(patientMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientManager.getById(id);
        patientMapper.updatePatient(patientRequestDTO, patient);
        Patient updated = patientManager.save(patient);
        return patientMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        patientManager.deleteById(id);
    }
}
