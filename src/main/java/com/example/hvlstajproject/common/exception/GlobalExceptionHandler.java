package com.example.hvlstajproject.common.exception;

import com.example.hvlstajproject.common.exception.common.DuplicateTcNoException;
import com.example.hvlstajproject.common.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.common.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.common.exception.patient.PatientNotFoundException;
import com.example.hvlstajproject.common.exception.appointment.AppointmentNotFoundException;
import com.example.hvlstajproject.common.exception.appointment.AppointmentNotModifiableException;
import com.example.hvlstajproject.common.exception.appointment.DoctorAppointmentConflictException;
import com.example.hvlstajproject.common.exception.appointment.PastAppointmentException;
import com.example.hvlstajproject.common.exception.appointment.PatientAppointmentConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateTcNoException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateTcNoException(DuplicateTcNoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DuplicateTelNoException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateTelNoException(DuplicateTelNoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDoctorNotFoundException(DoctorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map <String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(PastAppointmentException.class)
    public ResponseEntity<Map<String, String>> handlePastAppointmentException(PastAppointmentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(DoctorAppointmentConflictException.class)
    public ResponseEntity<Map<String, String>> handleDoctorAppointmentConflictException(DoctorAppointmentConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(PatientAppointmentConflictException.class)
    public ResponseEntity<Map<String, String>> handlePatientAppointmentConflictException(PatientAppointmentConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(AppointmentNotModifiableException.class)
    public ResponseEntity<Map<String, String>> handleAppointmentNotModifiableException(AppointmentNotModifiableException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }
}
