package com.example.hvlstajproject.repository;

import com.example.hvlstajproject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    boolean existsByTelNo(String telNo);
    boolean existsByTelNoAndIdNot(String telNo, Long id);
}
