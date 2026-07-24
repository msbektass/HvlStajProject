package com.example.hvlstajproject.service.impl;

import com.example.hvlstajproject.dto.DoctorRequestDTO;
import com.example.hvlstajproject.dto.DoctorResponseDTO;
import com.example.hvlstajproject.entity.Doctor;
import com.example.hvlstajproject.exception.DoctorNotFoundException;
import com.example.hvlstajproject.exception.DuplicateTelNoException;
import com.example.hvlstajproject.mapper.DoctorMapper;
import com.example.hvlstajproject.repository.DoctorRepository;
import com.example.hvlstajproject.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO) {
        if(doctorRepository.existsByTelNo(doctorRequestDTO.getTelNo())) {
            throw new DuplicateTelNoException(doctorRequestDTO.getTelNo());
        }
        Doctor doctor = doctorMapper.toDoctor(doctorRequestDTO);
        Doctor saved =  doctorRepository.save(doctor);
        return doctorMapper.toResponseDTO(saved);
    }
    @Transactional
    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorRequestDTO) {
        if(doctorRepository.existsByTelNoAndIdNot(doctorRequestDTO.getTelNo(), id)) {
            throw new DuplicateTelNoException(doctorRequestDTO.getTelNo());
        }
        Doctor doctor = doctorMapper.toDoctor(doctorRequestDTO);
        doctorMapper.updateDoctor(doctorRequestDTO, doctor);
        Doctor updated =  doctorRepository.save(doctor);
        return doctorMapper.toResponseDTO(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.getOne(id);
        return doctorMapper.toResponseDTO(doctor);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(doctorMapper :: toResponseDTO).toList();
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = findDoctorById(id);
        doctorRepository.delete(doctor);
    }

    private Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
    }
}
