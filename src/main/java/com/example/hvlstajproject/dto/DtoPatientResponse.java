package com.example.hvlstajproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatientResponse {
    private String tcNo;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
