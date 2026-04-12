package com.hospital.controller;


import com.hospital.dto.response.DepartmentResponse;
import com.hospital.dto.response.SpecializationResponse;
import com.hospital.service.DepartmentService;
import com.hospital.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialization")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody List<String> names){
        specializationService.saveAllSpecialization(names);
        return ResponseEntity.ok("Specialization saved");
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecializationResponse>> getAll(){
        return ResponseEntity.ok(specializationService.getAll());
    }
}
