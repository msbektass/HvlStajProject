package com.example.hvlstajproject.patient.manager;

import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.mapper.PatientMapper;
import com.example.hvlstajproject.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientManager {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientResponseDTO create(PatientRequestDTO requestDTO) {
        Patient patient = patientMapper.toPatient(requestDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponseDto(savedPatient);
    }

    public PatientResponseDTO update(Patient patient, PatientRequestDTO requestDTO) {
        patientMapper.updatePatient(requestDTO, patient);
        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toResponseDto(updatedPatient);
    }

    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return patientMapper.toResponseDto(patient);
    }

    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll().stream().map(patientMapper::toResponseDto).toList();
    }

    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    public boolean existsByTcNo(String tcNo) {
        return patientRepository.existsByTcNo(tcNo);
    }

    public boolean existsByTcNoAndIdNot(String tcNo, Long id) {
        return patientRepository.existsByTcNoAndIdNot(tcNo, id);
    }
}
