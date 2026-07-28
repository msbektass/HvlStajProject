package com.example.hvlstajproject.doctor.service;

import com.example.hvlstajproject.common.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.entity.Doctor;
import com.example.hvlstajproject.doctor.manager.DoctorManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorManager doctorManager;

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO requestDTO) {
        checkDuplicateTelNo(requestDTO.getTelNo(), null);
        return doctorManager.create(requestDTO);
    }

    @Override
    @Transactional
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO requestDTO) {
        Doctor doctor = getDoctorOrThrow(id);
        checkDuplicateTelNo(requestDTO.getTelNo(), id);
        return doctorManager.update(doctor, requestDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = getDoctorOrThrow(id);
        return doctorManager.toResponseDTO(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorManager.findAll();
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorOrThrow(id);
        doctorManager.delete(doctor);
    }

    private Doctor getDoctorOrThrow(Long id) {
        return doctorManager.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
    }

    private void checkDuplicateTelNo(String telNo, Long excludedDoctorId) {
        boolean duplicate;
        if (excludedDoctorId == null) {
            duplicate = doctorManager.existsByTelNo(telNo);
        } else {
            duplicate = doctorManager.existsByTelNoAndIdNot(telNo, excludedDoctorId
            );
        }
        if (duplicate) {
            throw new DuplicateTelNoException(telNo);
        }
    }
}
