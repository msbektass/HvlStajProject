package com.example.hvlstajproject.doctor.controller;

import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Doktor işlemleri")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors" )
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "Bütün doktorları listeleme")
    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @Operation(summary = "Doktor ekleme")
    @PostMapping
    public DoctorResponseDTO addDoctor(@Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return doctorService.createDoctor(doctorRequestDTO);
    }

    @Operation(summary = "Id ile doktor çekme")
    @GetMapping(path= "/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @Operation(summary = "Doktor bilgilerini güncelleme")
    @PutMapping(path = "/{id}")
    public DoctorResponseDTO updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return doctorService.updateDoctor(id, doctorRequestDTO);
    }

    @Operation(summary = "Doktor silme")
    @DeleteMapping(path = "/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
