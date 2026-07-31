package com.example.hvlstajproject.appointment.dto;

import com.example.hvlstajproject.common.enums.EAppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentStatusChangeDTO {

    @NotNull(message = "Randevu durumu boş bırakılamaz")
    private EAppointmentStatus status;

}
