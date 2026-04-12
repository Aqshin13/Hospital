//package com.hospital.repository;
//
//import com.hospital.entity.Doctor;
//import com.hospital.entity.DoctorSchedule;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.DayOfWeek;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,Long> {
//
//    Optional<DoctorSchedule> findByDoctor_UserIdAndDayOfWeek(UUID doctorUserId,
//                                                             DayOfWeek dayOfWeek);
//
//}
