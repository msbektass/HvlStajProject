package com.example.hvlstajproject.doctor.dto;

import com.example.hvlstajproject.common.enums.EGender;
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
    private EGender gender;
}
