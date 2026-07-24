package com.example.hvlstajproject.service;

import com.example.hvlstajproject.dto.DoctorRequestDTO;
import com.example.hvlstajproject.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {
    DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO getDoctorById(Long id);
    List<DoctorResponseDTO> getAllDoctors();
    void deleteDoctor(Long id);
}
