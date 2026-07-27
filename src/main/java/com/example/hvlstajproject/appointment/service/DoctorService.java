package com.example.hvlstajproject.appointment.service;

import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {
    DoctorResponseDTO createDoctor(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO getDoctorById(Long id);
    List<DoctorResponseDTO> getAllDoctors();
    void deleteDoctor(Long id);
}
