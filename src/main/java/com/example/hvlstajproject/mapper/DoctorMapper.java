package com.example.hvlstajproject.mapper;

import com.example.hvlstajproject.dto.DoctorRequestDTO;
import com.example.hvlstajproject.dto.DoctorResponseDTO;
import com.example.hvlstajproject.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "id", ignore = true)
    Doctor toDoctor(DoctorRequestDTO doctorRequestDTO);
    DoctorResponseDTO toResponseDTO(Doctor doctor);
    @Mapping(target = "id", ignore = true)
    void updateDoctor(DoctorRequestDTO doctorRequestDTO, @MappingTarget Doctor doctor);
}
