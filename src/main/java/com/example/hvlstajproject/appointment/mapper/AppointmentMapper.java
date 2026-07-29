package com.example.hvlstajproject.appointment.mapper;

import com.example.hvlstajproject.appointment.dto.AppointmentRequestDTO;
import com.example.hvlstajproject.appointment.dto.AppointmentResponseDTO;
import com.example.hvlstajproject.appointment.entity.Appointment;
import com.example.hvlstajproject.doctor.mapper.DoctorMapper;
import com.example.hvlstajproject.patient.mapper.PatientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DoctorMapper.class})
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "status", ignore = true)
    Appointment toAppointment(AppointmentRequestDTO appointmentRequestDTO);

    AppointmentResponseDTO toResponseDTO(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateAppointment(AppointmentRequestDTO appointmentRequestDTO, @MappingTarget Appointment appointment);
}