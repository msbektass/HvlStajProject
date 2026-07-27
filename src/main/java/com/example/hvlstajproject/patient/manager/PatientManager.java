package com.example.hvlstajproject.patient.manager;

import com.example.hvlstajproject.exception.common.DuplicateTcNoException;
import com.example.hvlstajproject.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.patient.entity.Patient;
import com.example.hvlstajproject.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PatientManager {

    private final PatientRepository patientRepository;

    public Patient save(Patient  patient) {
        checkDuplicateTcNo(patient);
        return patientRepository.save(patient);
    }

    public Patient getById(Long id){
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public List<Patient> getAll(){
        return patientRepository.findAll();
    }

    public void deleteById(Long id){
        Patient patient = getById(id);
        patientRepository.delete(patient);
    }

    private void checkDuplicateTcNo(Patient patient){
        boolean duplicate;
        if (patient.getId() == null){
            duplicate = patientRepository.existsByTcNo(patient.getTcNo());
        }
        else{
            duplicate = patientRepository.existsByTcNoAndIdNot(patient.getTcNo(), patient.getId());
        }
        if(duplicate){
            throw new DuplicateTcNoException(patient.getTcNo());
        }
    }
}
