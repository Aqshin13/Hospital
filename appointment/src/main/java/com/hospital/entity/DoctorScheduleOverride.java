//package com.hospital.entity;
//
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;import java.util.UUID;
//
//
//@Entity
//@Getter @Setter @Builder
//@NoArgsConstructor @AllArgsConstructor
//public class DoctorScheduleOverride extends BaseEntity{
//
//
//    private UUID userId;
//
//    @Column(nullable = false)
//    private LocalDate overrideDate;
//
//    @Column(nullable = false)
//    private boolean isWorking;
//
//    private LocalTime workStart;
//    private LocalTime workEnd;
//}