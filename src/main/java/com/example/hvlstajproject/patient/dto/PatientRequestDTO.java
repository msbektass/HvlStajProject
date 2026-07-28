package com.example.hvlstajproject.patient.dto;

import com.example.hvlstajproject.common.enums.EGender;
import com.example.hvlstajproject.common.validation.TcNoValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {

    @TcNoValidation
    private String tcNo;

    @NotBlank(message = "Ad boş olamaz")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    private String lastName;

    @NotNull(message = "Doğum tarihi boş olamaz")
    private LocalDate birthDate;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta girilmelidir")
    private String email;

    @NotBlank(message = "Telefon numarası boş olamaz")
    private String telNo;

    @NotBlank(message = "Adres boş olamaz")
    private String address;

    @NotNull(message = "Cinsiyet bilgisi boş bırakılamaz")
    private EGender gender;
}
