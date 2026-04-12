package com.hospital.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorSlot extends BaseEntity{


    private UUID doctorUserId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    boolean isBooked=false;
}
