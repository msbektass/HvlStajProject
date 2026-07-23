package com.example.hvlstajproject.controller;

import com.example.hvlstajproject.dto.DtoPatientRequest;
import com.example.hvlstajproject.dto.DtoPatientResponse;
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
    public List<DtoPatientResponse> getAllPatients() {
        return patientService.getAllPatients();
    }
    @PostMapping
    public DtoPatientResponse addPatient(@Valid @RequestBody DtoPatientRequest dtoPatientRequest) {
        return patientService.createPatient(dtoPatientRequest);
    }
    @GetMapping(path = "/{id}")
    public DtoPatientResponse getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }
    @PutMapping(path = "/{id}")
    public DtoPatientResponse updatePatient( @PathVariable Long id, @Valid @RequestBody DtoPatientRequest dtoPatientRequest) {
        return patientService.updatePatient(id, dtoPatientRequest);
    }
    @DeleteMapping(path = "/{id}")
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }
}
