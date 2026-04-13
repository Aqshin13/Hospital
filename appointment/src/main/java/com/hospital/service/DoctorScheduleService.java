package com.hospital.service;


import com.hospital.dto.request.ScheduleCreateRequest;
import com.hospital.entity.DoctorSchedule;
import com.hospital.entity.DoctorSlot;
import com.hospital.repository.DoctorScheduleRepository;
import com.hospital.repository.DoctorSlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {


    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorSlotRepository doctorSlotRepository;


    @Transactional(rollbackOn = Exception.class)
    public void createScheduleAndSlots(UUID doctorAuthId,
                                       List<ScheduleCreateRequest> requests){

        List<LocalDate> dates = requests.stream()
                .map(ScheduleCreateRequest::date)
                .toList();
        List<LocalDate> conflicting = doctorScheduleRepository
                .findExistingDates(doctorAuthId, dates);
        if (!conflicting.isEmpty()) {
            throw new RuntimeException(
                    "Konflikt olan tarixlər: " + conflicting
            );
        }
        List<DoctorSchedule> schedules = requests.stream()
                .map(x->DoctorSchedule.builder()
                        .date(x.date())
                        .slotDuration(x.duration())
                        .workStart(x.workStart())
                        .workEnd(x.workEnd())
                        .userId(doctorAuthId)
                        .build())
                .toList();
        schedules = doctorScheduleRepository.saveAll(schedules);
        List<DoctorSlot> allSlots = schedules.stream()
                .flatMap(schedule -> buildSlots(schedule).stream())
                .toList();
        doctorSlotRepository.saveAll(allSlots);

    }



    private List<DoctorSlot> buildSlots(DoctorSchedule schedule) {
        List<DoctorSlot> slots = new ArrayList<>();
        LocalTime current = schedule.getWorkStart();

        while (!current.plusMinutes(schedule.getSlotDuration()).isAfter(schedule.getWorkEnd())) {
            slots.add(DoctorSlot.builder()
                    .doctorScheduleId(schedule.getId())
                    .doctorUserId(schedule.getUserId())
                    .date(schedule.getDate())
                    .startTime(current)
                    .endTime(current.plusMinutes(schedule.getSlotDuration()))
                    .isBooked(false)
                    .build());

            current = current.plusMinutes(schedule.getSlotDuration());
        }
        return slots;
    }


}
