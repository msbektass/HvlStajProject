package com.example.hvlstajproject.repository;

import com.example.hvlstajproject.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByTcNo(String tcNo);
    boolean existsByTcNoAndIdNot(String tcNo, Long id);
}
