package com.example.hvlstajproject.doctor.service;

import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.manager.DoctorManager;
import com.example.hvlstajproject.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.doctor.mapper.DoctorMapper;
import com.example.hvlstajproject.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorManager doctorManager;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorMapper.toDoctor(doctorRequestDTO);
        Doctor saved =  doctorManager.save(doctor);
        return doctorMapper.toResponseDTO(saved);
    }
    @Transactional
    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorManager.getById(id);
        doctorMapper.updateDoctor(doctorRequestDTO, doctor);
        Doctor updated =  doctorManager.save(doctor);
        return doctorMapper.toResponseDTO(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorManager.getById(id);
        return doctorMapper.toResponseDTO(doctor);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorManager.getAll().stream().map(doctorMapper :: toResponseDTO).toList();
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) {
        doctorManager.deleteById(id);
    }
}
