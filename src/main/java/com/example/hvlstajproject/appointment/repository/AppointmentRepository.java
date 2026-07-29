package com.example.hvlstajproject.appointment.repository;

import com.example.hvlstajproject.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTime(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime);

    boolean existsByPatient_IdAndAppointmentDateAndAppointmentTime(Long patientId, LocalDate appointmentDate, LocalTime appointmentTime);

    boolean existsByDoctor_IdAndAppointmentDateAndAppointmentTimeAndIdNot(Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime, Long excludedAppointmentId);

    boolean existsByPatient_IdAndAppointmentDateAndAppointmentTimeAndIdNot(Long patientId, LocalDate appointmentDate, LocalTime appointmentTime, Long excludedAppointmentId);
}