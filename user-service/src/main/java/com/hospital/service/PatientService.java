package com.hospital.service;

import com.hospital.dto.event.UserCompletedEvent;
import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.dto.request.PatientCreateRequest;
import com.hospital.entity.Doctor;
import com.hospital.entity.Outbox;
import com.hospital.entity.Patient;
import com.hospital.repository.OutboxRepository;
import com.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {


    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;


    @Transactional(rollbackOn = Exception.class)
    public void savePatient(UUID authId, PatientCreateRequest patientCreateRequest) {
        Patient patient = Patient.builder()
                .address(patientCreateRequest.getAddress())
                .bloodGroup(patientCreateRequest.getBloodGroup())
                .gender(patientCreateRequest.getGender())
                .userId(authId)
                .firstName(patientCreateRequest.getFirstName())
                .lastName(patientCreateRequest.getLastName())
                .phone(patientCreateRequest.getPhone())
                .build();
        patientRepository.save(patient);
        UserCompletedEvent userCompletedEvent = new UserCompletedEvent(patient.getUserId());
        Outbox outbox = Outbox.builder()
                .aggregateType("user-complete")
                .type("patientIsCompleted")
                .aggregateId(patient.getUserId().toString())
                .payload(objectMapper.writeValueAsString(userCompletedEvent))
                .build();
        outboxRepository.save(outbox);

    }


}
