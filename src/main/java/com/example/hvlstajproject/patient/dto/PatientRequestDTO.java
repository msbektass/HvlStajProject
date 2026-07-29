package com.example.hvlstajproject.patient.dto;

import com.example.hvlstajproject.common.enums.EGender;
import com.example.hvlstajproject.common.validation.EmailValidation;
import com.example.hvlstajproject.common.validation.TcNoValidation;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {

    @TcNoValidation(message = "Geçerli bir TC Kimlik Numarası giriniz")
    private String tcNo;

    @NotBlank(message = "Ad boş olamaz")
    @Size(max = 50, message = "Ad en fazla 50 karakter olmalı")
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    @Size(max = 50, message = "Soyad en fazla 50 karakter olmalı")
    private String lastName;

    @NotNull(message = "Doğum tarihi boş olamaz")
    @PastOrPresent(message = "Geçerli bir doğum tarihi giriniz")
    private LocalDate birthDate;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta girilmelidir")
    @Size(max = 100, message = "E-posta en fazla 100 karakter olmalı")
    @EmailValidation(message = "E-postada @ işaretinden sonrası küçük karakter olmalı")
    private String email;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\+905[0-9]{9}$", message = "Geçerli bir telefon numarası giriniz")
    private String telNo;

    @NotBlank(message = "Adres boş olamaz")
    @Size(max = 255, message = "Adres en fazla 255 karakter olmalı")
    private String address;

    @NotNull(message = "Cinsiyet bilgisi boş bırakılamaz")
    private EGender gender;
}
