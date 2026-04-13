package com.hospital.controller;

import com.hospital.dto.request.ScheduleCreateRequest;
import com.hospital.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class DoctorScheduleController {




    private final DoctorScheduleService doctorScheduleService;



    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody List<ScheduleCreateRequest> request
    ,@RequestHeader("user-id")UUID doctorUserid
    ){
        doctorScheduleService.createScheduleAndSlots(doctorUserid,request);
        return ResponseEntity.ok("Your schedule and slots are created successfully");

    }






}
