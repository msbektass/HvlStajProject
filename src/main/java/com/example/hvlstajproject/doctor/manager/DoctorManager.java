package com.example.hvlstajproject.doctor.manager;

import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.mapper.DoctorMapper;
import com.example.hvlstajproject.doctor.repository.DoctorRepository;
import com.example.hvlstajproject.common.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorManager {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponseDTO create(DoctorRequestDTO requestDTO) {
        Doctor doctor = doctorMapper.toDoctor(requestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponseDTO(savedDoctor);
    }

    public DoctorResponseDTO update(Doctor doctor, DoctorRequestDTO requestDTO) {
        doctorMapper.updateDoctor(requestDTO, doctor);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toResponseDTO(updatedDoctor);
    }

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public DoctorResponseDTO toResponseDTO(Doctor doctor) {
        return doctorMapper.toResponseDTO(doctor);
    }

    public List<DoctorResponseDTO> findAll() {
        return doctorRepository.findAll().stream().map(doctorMapper::toResponseDTO).toList();
    }

    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    public boolean existsByTelNo(String telNo) {
        return doctorRepository.existsByTelNo(telNo);
    }

    public boolean existsByTelNoAndIdNot(String telNo, Long id) {
        return doctorRepository.existsByTelNoAndIdNot(telNo, id);
    }
}
