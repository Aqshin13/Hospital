package com.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Appointment extends BaseEntity{



    private UUID patientUserId;

    @OneToOne
    private DoctorSlot doctorSlot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String notes;
    @Version
    private Long version;

    public enum AppointmentStatus {
        SCHEDULED, CANCELLED, COMPLETED,NO_SHOW
    }
}