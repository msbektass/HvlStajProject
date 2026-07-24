package com.example.hvlstajproject.mapper;

import com.example.hvlstajproject.dto.DoctorRequestDTO;
import com.example.hvlstajproject.dto.DoctorResponseDTO;
import com.example.hvlstajproject.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toDoctor(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO toResponseDTO(Doctor doctor);
    void updateDoctor(DoctorRequestDTO doctorRequestDTO, @MappingTarget Doctor doctor);
}
