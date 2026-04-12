package com.hospital.repository;

import com.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

//
//    @Query("""
//        SELECT COUNT(a) > 0 FROM Appointment a
//        WHERE a.doctorUserId = :doctorId
//          AND a.appointmentDate = :date
//          AND (a.status = 'SCHEDULED' or a.status='CANCELED')
//          AND a.startTime < :endTime
//          AND a.endTime > :startTime
//    """)
//    boolean existsOverlappingSlot(
//            @Param("doctorId") UUID doctorId,
//            @Param("date") LocalDate date,
//            @Param("startTime") LocalTime startTime,
//            @Param("endTime") LocalTime endTime
//    );
//
//    @Query("""
//        SELECT a.startTime FROM Appointment a
//        WHERE a.doctorUserId = :doctorId
//          AND a.appointmentDate = :date
//          AND a.status = 'SCHEDULED'
//        ORDER BY a.startTime
//    """)
//    List<LocalTime> findBookedStartTimes(
//            @Param("doctorId") UUID doctorId,
//            @Param("date") LocalDate date
//    );
//
//
//    List<Appointment> findByPatientUserIdOrderByAppointmentDateAscStartTimeAsc(UUID patientId);
}
