package com.example.hvlstajproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {
    @NotBlank(message = "TC kimlik numarası boş olamaz")
    @Pattern(regexp = "\\d{11}", message = "TC kimlik numarası 11 rakamdan oluşmalıdır")
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
}
