package com.example.hvlstajproject.patient.dto;

import com.example.hvlstajproject.common.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private String tcNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private EGender gender;
}
