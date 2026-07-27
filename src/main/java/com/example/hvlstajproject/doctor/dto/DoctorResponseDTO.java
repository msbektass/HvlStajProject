package com.example.hvlstajproject.doctor.dto;

import com.example.hvlstajproject.enums.Gender;
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
    private Gender gender;
}
