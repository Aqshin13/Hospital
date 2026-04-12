package com.hospital.controller;

import com.hospital.dto.response.DepartmentResponse;
import com.hospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody List<String> names){
        departmentService.saveAllDepartment(names);
        return ResponseEntity.ok("Departments saved");
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentResponse>> getAll(){
        return ResponseEntity.ok(departmentService.getAll());
    }


}
