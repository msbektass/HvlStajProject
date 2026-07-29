package com.example.hvlstajproject.doctor.mapper;

import com.example.hvlstajproject.doctor.dto.DoctorRequestDTO;
import com.example.hvlstajproject.doctor.dto.DoctorResponseDTO;
import com.example.hvlstajproject.doctor.entity.Doctor;
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
