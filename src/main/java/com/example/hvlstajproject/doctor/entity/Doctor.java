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

    @Column(name = "first_name",nullable = false )
    private String  firstName;

    @Column(name = "last_name", nullable = false )
    private String lastName;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false, unique = true)
    private String telNo;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGender gender;
}
