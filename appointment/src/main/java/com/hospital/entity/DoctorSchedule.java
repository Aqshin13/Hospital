package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule extends BaseEntity {
    private UUID userId;
    private LocalDate date;
    private LocalTime workStart;
    private LocalTime workEnd;
    private int slotDuration;
}