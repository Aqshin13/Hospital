package com.hospital.controller;


import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/complete")
    public ResponseEntity<?> completeDoctorRegistration(@RequestBody DoctorCreateRequest doctorCreateRequest
    ,@RequestHeader("user-id") UUID authId
    ){
    doctorService.saveDoctor(authId, doctorCreateRequest);
    return ResponseEntity.ok("Your profile is being completed");
    }

}
