package com.example.hvlstajproject.mapper;

import com.example.hvlstajproject.dto.DtoPatientRequest;
import com.example.hvlstajproject.dto.DtoPatientResponse;
import com.example.hvlstajproject.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "id", ignore = true)
    Patient toPatient(DtoPatientRequest dtoPatientRequest);
    DtoPatientResponse toResponseDto(Patient patient);
    @Mapping(target = "id", ignore = true)
    void updatePatient(DtoPatientRequest dtoPatientRequest, @MappingTarget Patient patient);
}
