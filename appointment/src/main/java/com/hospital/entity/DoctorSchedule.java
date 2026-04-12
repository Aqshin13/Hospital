//package com.hospital.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.UUID;
//
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class DoctorSchedule extends BaseEntity {
//
//
//    private UUID userId;
//
//    @Enumerated(EnumType.STRING)
//    private DayOfWeek dayOfWeek;
//
//    private LocalTime workStart;
//
//    private LocalTime workEnd;
//
//    private boolean isWorking;
//}