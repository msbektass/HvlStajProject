package com.example.hvlstajproject.doctor.manager;

import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.repository.DoctorRepository;
import com.example.hvlstajproject.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.exception.doctor.DoctorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorManager {

    private final DoctorRepository doctorRepository;

    public Doctor save(Doctor doctor){
        checkDuplicateTelNo(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor getById(Long id){
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
    }

    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }

    public void deleteById(Long id){
        Doctor doctor = getById(id);
        doctorRepository.delete(doctor);
    }

    private void checkDuplicateTelNo(Doctor doctor){
        boolean duplicate;
        if(doctor.getId() == null){
            duplicate = doctorRepository.existsByTelNo(doctor.getTelNo());
        }
        else{
            duplicate = doctorRepository.existsByTelNoAndIdNot(doctor.getTelNo(), doctor.getId());
        }
        if(duplicate){
            throw new DuplicateTelNoException(doctor.getTelNo());
        }
    }
}
