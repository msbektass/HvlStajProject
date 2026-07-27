package com.example.hvlstajproject.exception;

import com.example.hvlstajproject.exception.common.DuplicateTcNoException;
import com.example.hvlstajproject.exception.common.DuplicateTelNoException;
import com.example.hvlstajproject.exception.doctor.DoctorNotFoundException;
import com.example.hvlstajproject.exception.patient.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
