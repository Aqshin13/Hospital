package com.hospital.service;


import com.hospital.dto.response.DepartmentResponse;
import com.hospital.entity.Department;
import com.hospital.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public void saveAllDepartment(List<String> departments){
        List<Department> departmentList=departments.stream()
                .map(name->Department.builder().name(name).build())
                .toList();
        departmentRepository.saveAll(departmentList);

    }


    public List<DepartmentResponse> getAll() {
        return  departmentRepository.findAll()
                .stream()
                .map(x->new DepartmentResponse(x.getId(), x.getName())).toList();
    }
}
