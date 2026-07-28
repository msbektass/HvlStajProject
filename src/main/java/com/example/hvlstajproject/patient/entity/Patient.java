package com.example.hvlstajproject.patient.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import com.example.hvlstajproject.common.enums.EGender;
@Getter
@Setter
@Entity
@Table(name = "patient")
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tc_no", nullable = false, unique = true, length = 11)
    private String tcNo;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "tel_no", nullable = false, unique = true, length = 13)
    private String telNo;

    @Column(nullable = false, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EGender gender;
}
