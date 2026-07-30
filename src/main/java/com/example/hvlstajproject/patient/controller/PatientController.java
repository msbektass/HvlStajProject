package com.example.hvlstajproject.patient.controller;

import com.example.hvlstajproject.patient.dto.PatientRequestDTO;
import com.example.hvlstajproject.patient.dto.PatientResponseDTO;
import com.example.hvlstajproject.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hasta işlemleri")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Bütün hastaları listeleme")
    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }

    @Operation(summary = "Hasta ekleme")
    @PostMapping
    public PatientResponseDTO addPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return patientService.createPatient(patientRequestDTO);
    }

    @Operation(summary = "Id ile hasta çekme")
    @GetMapping(path = "/{id}")
    public PatientResponseDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @Operation(summary = "Hasta güncelleme")
    @PutMapping(path = "/{id}")
    public PatientResponseDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return patientService.updatePatient(id, patientRequestDTO);
    }

    @Operation(summary = "Hasta silme")
    @DeleteMapping(path = "/{id}")
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }
}
