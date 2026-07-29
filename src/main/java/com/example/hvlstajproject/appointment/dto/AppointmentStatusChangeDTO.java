package com.example.hvlstajproject.appointment.dto;

import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentStatusChangeDTO {

    @NotNull(message = "Randevu durumu boş bırakılamaz")
    private EAppointmentStatus status;

}
