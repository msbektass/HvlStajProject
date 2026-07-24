package com.example.hvlstajproject.mapper;

import com.example.hvlstajproject.dto.PatientRequestDTO;
import com.example.hvlstajproject.dto.PatientResponseDTO;
import com.example.hvlstajproject.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "id", ignore = true)
    Patient toPatient(PatientRequestDTO patientRequestDTO);
    PatientResponseDTO toResponseDto(Patient patient);
    @Mapping(target = "id", ignore = true)
    void updatePatient(PatientRequestDTO patientRequestDTO, @MappingTarget Patient patient);
}
