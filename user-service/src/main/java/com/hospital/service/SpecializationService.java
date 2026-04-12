package com.hospital.service;


import com.hospital.dto.response.DepartmentResponse;
import com.hospital.dto.response.SpecializationResponse;
import com.hospital.entity.Department;
import com.hospital.entity.Specialization;
import com.hospital.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationService {


    private final SpecializationRepository specializationRepository;


    public void saveAllSpecialization(List<String> specialization) {
        List<Specialization> specializationList = specialization
                .stream()
                .map(name -> {
                    Specialization specializationMap = Specialization.builder().name(name).build();
                    return specializationMap;
                })
                .toList();
        specializationRepository.saveAll(specializationList);


    }


    public List<SpecializationResponse> getAll() {
        return specializationRepository.findAll()
                .stream()
                .map(x -> new SpecializationResponse(x.getId(), x.getName())).toList();
    }

}
