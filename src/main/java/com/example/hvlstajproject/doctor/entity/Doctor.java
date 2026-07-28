package com.example.hvlstajproject.doctor.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.hvlstajproject.common.enums.EGender;

@Entity
@Setter
@Getter
@Table(name = "doctor")
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false, length = 50)
    private String  firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 75)
    private String branch;

    @Column(name = "tel_no", nullable = false, unique = true, length = 13)
    private String telNo;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EGender gender;
}
