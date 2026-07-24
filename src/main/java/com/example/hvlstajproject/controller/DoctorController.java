package com.example.hvlstajproject.controller;

import com.example.hvlstajproject.dto.DoctorRequestDTO;
import com.example.hvlstajproject.dto.DoctorResponseDTO;
import com.example.hvlstajproject.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors" )
public class DoctorController {
    private final DoctorService doctorService;
    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
    @PostMapping
    public DoctorResponseDTO addDoctor(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return doctorService.createDoctor(doctorRequestDTO);
    }
    @GetMapping(path= "/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }
    @PutMapping(path = "/{id}")
    public DoctorResponseDTO updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return doctorService.updateDoctor(id, doctorRequestDTO);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
