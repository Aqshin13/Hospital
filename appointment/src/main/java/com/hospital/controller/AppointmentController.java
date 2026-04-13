package com.hospital.controller;


import com.hospital.dto.request.AppointmentRequest;
import com.hospital.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService appointmentService;



    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request,
                                               @RequestHeader("user-id")UUID authPatientId){
        appointmentService.createAppointment(authPatientId,request);
        return ResponseEntity.ok("Appointment is created");
    }




}
