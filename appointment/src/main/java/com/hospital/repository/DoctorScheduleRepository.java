package com.hospital.repository;

import com.hospital.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,Long> {

    @Query("""
    SELECT s.date FROM DoctorSchedule s
    WHERE s.userId = :doctorId
    AND s.date IN :dates
    """)
    List<LocalDate> findExistingDates(
            @Param("doctorId") UUID doctorId,
            @Param("dates") List<LocalDate> dates
    );

}
