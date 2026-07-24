package com.example.hvlstajproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private String gender;
}
