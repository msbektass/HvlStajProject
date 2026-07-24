package com.example.hvlstajproject.controller;

import com.example.hvlstajproject.dto.PatientRequestDTO;
import com.example.hvlstajproject.dto.PatientResponseDTO;
import com.example.hvlstajproject.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientResponseDTO> getAllPatients() {
        return patientService.getAllPatients();
    }
    @PostMapping
    public PatientResponseDTO addPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return patientService.createPatient(patientRequestDTO);
    }
    @GetMapping(path = "/{id}")
    public PatientResponseDTO getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }
    @PutMapping(path = "/{id}")
    public PatientResponseDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return patientService.updatePatient(id, patientRequestDTO);
    }
    @DeleteMapping(path = "/{id}")
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }
}
