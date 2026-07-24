package com.example.hvlstajproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private String firstName;
    private String lastName;
    private String branch;
    private String gender;
}
