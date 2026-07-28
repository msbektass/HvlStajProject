package com.example.hvlstajproject.doctor.dto;

import com.example.hvlstajproject.common.enums.EGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO {
    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 50, message = "Ad en fazla 50 karakter olmalı")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(max = 50, message = "Soyad en fazla 50 karakter olmalı")
    private String lastName;

    @NotBlank(message = "Branş bilgisi boş bırakılamaz")
    @Size(max = 75, message = "Branş bilgisi en fazla 75 karakter olmalı")
    private String branch;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\+905[0-9]{9}$", message = "Geçerli bir telefon numarası giriniz")
    private String telNo;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta girilmelidir")
    @Size(max = 100, message = "E-posta en fazla 100 karakter olmalı")
    private String email;

    @NotBlank(message = "Adres boş olamaz")
    @Size(max = 255, message = "Adres en fazla 255 karakter olmalı")
    private String address;

    @NotNull(message = "Cinsiyet bilgisi boş bırakılamaz")
    private EGender gender;
}
