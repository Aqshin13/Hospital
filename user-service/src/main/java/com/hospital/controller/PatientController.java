package com.hospital.controller;

import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.dto.request.PatientCreateRequest;
import com.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/patinet")
public class PatientController {


    private final PatientService patientService;

    @PostMapping("/complete")
    public ResponseEntity<?> completeDoctorRegistration(@RequestBody PatientCreateRequest patientCreateRequest
            , @RequestHeader("user-id") UUID authId
    ){
        patientService.savePatient(authId,patientCreateRequest);
        return ResponseEntity.ok("Your profile is being completed");
    }



}
