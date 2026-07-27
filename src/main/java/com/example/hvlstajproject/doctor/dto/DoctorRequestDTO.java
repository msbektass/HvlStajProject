package com.example.hvlstajproject.doctor.dto;

import com.example.hvlstajproject.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO {
    @NotBlank(message = "Ad boş olamaz")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    private String lastName;

    @NotBlank(message = "Branş bilgisi boş bırakılamaz")
    private String branch;

    @NotBlank(message = "Telefon numarası boş olamaz")
    private String telNo;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta girilmelidir")
    private String email;

    @NotBlank(message = "Adres boş olamaz")
    private String address;
    
    @NotNull(message = "Cinsiyet bilgisi boş bırakılamaz")
    private Gender gender;
}
