package com.hospital.service;


import com.hospital.dto.event.UserCompletedEvent;
import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.entity.Doctor;
import com.hospital.entity.Outbox;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ObjectMapper  objectMapper;
    private final OutboxRepository outboxRepository;

    @Transactional(rollbackOn = Exception.class)
    public void saveDoctor(UUID authId, DoctorCreateRequest doctorCreateRequest){
        Doctor doctor= Doctor.builder()
                .firstName(doctorCreateRequest.getFirstName())
                .lastName(doctorCreateRequest.getLastName())
                .experienceYears(doctorCreateRequest.getExperienceYears())
                .phone(doctorCreateRequest.getPhone())
                .userId(authId)
                .build();
        doctorRepository.save(doctor);
        UserCompletedEvent userCompletedEvent=new UserCompletedEvent(doctor.getUserId());
        Outbox outbox= Outbox.builder()
                .aggregateType("doctor-complete")
                .type("doctorIsCompleted")
                .aggregateId(doctor.getUserId().toString())
                .payload(objectMapper.writeValueAsString(userCompletedEvent))
                .build();
        outboxRepository.save(outbox);

    }


}
