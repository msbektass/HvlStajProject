package com.example.hvlstajproject.service.impl;

import com.example.hvlstajproject.dto.DtoPatientRequest;
import com.example.hvlstajproject.dto.DtoPatientResponse;
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
    public DtoPatientResponse createPatient(DtoPatientRequest dtoPatientRequest) {
        if(patientRepository.existsByTcNo(dtoPatientRequest.getTcNo())) {
            throw new DuplicateTcNoException(dtoPatientRequest.getTcNo());
        }
        Patient patient = patientMapper.toPatient(dtoPatientRequest);
        Patient saved = patientRepository.save(patient);
        return patientMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DtoPatientResponse getPatientById(Long id) {
        Patient patient = findPatientById(id);
        return patientMapper.toResponseDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoPatientResponse> getAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public DtoPatientResponse updatePatient(Long id, DtoPatientRequest dtoPatientRequest) {
        if(patientRepository.existsByTcNoAndIdNot(dtoPatientRequest.getTcNo(), id)) {
            throw new DuplicateTcNoException(dtoPatientRequest.getTcNo());
        }
        Patient patient = findPatientById(id);
        patientMapper.updatePatient(dtoPatientRequest, patient);
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
