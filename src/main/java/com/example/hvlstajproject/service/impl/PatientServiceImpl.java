package com.example.hvlstajproject.service.impl;

import com.example.hvlstajproject.dto.PatientRequestDTO;
import com.example.hvlstajproject.dto.PatientResponseDTO;
import com.example.hvlstajproject.entity.Patient;
import com.example.hvlstajproject.exception.DuplicateTcNoException;
import com.example.hvlstajproject.exception.PatientNotFoundException;
import com.example.hvlstajproject.mapper.PatientMapper;
import com.example.hvlstajproject.repository.PatientRepository;
import com.example.hvlstajproject.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByTcNo(patientRequestDTO.getTcNo())) {
            throw new DuplicateTcNoException(patientRequestDTO.getTcNo());
        }
        Patient patient = patientMapper.toPatient(patientRequestDTO);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = findPatientById(id);
        return patientMapper.toResponseDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByTcNoAndIdNot(patientRequestDTO.getTcNo(), id)) {
            throw new DuplicateTcNoException(patientRequestDTO.getTcNo());
        }
        Patient patient = findPatientById(id);
        patientMapper.updatePatient(patientRequestDTO, patient);
        Patient updated = patientRepository.save(patient);
        return patientMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = findPatientById(id);
        patientRepository.delete(patient);
    }
    private Patient findPatientById(Long id){
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }
}
