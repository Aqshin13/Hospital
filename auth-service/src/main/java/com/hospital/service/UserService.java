package com.hospital.service;


import com.hospital.config.MailProducer;
import com.hospital.dto.event.ActivateUserEvent;
import com.hospital.dto.event.DoctorCreateEvent;
import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.dto.request.PatientCreateRequest;
import com.hospital.entity.Outbox;
import com.hospital.entity.OutboxRepository;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailProducer mailProducer;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;


    @Transactional(rollbackOn = Exception.class)
    public void saveDoctorUser(DoctorCreateRequest doctorCreateRequest) {
        User user = userRepository.save(User.builder()
                .email(doctorCreateRequest.getEmail())
                .role(User.Role.DOCTOR)
                .isActive(true)
                .status(User.Status.PENDING)
                .build());
        DoctorCreateEvent doctorCreateEvent = DoctorCreateEvent.builder()
                .userId(user.getId())
                .lastName(doctorCreateRequest.getLastName())
                .firstName(doctorCreateRequest.getFirstName())
                .build();
        Outbox outbox = Outbox.builder()
                .aggregateType("auth-create")
                .aggregateId(String.valueOf(user.getId()))
                .type("AuthDoctorCreate")
                .payload(objectMapper.writeValueAsString(doctorCreateEvent))
                .build();
        outboxRepository.save(outbox);

    }

    public void savePatientUser(PatientCreateRequest patientCreateRequest) {
        String activateToken = UUID.randomUUID().toString();
        userRepository.save(User.builder()
                .email(patientCreateRequest.getEmail())
                .role(User.Role.PATIENT)
                .activateToken(activateToken)
                .isActive(false)
                .build());
        mailProducer.sendActivate(new ActivateUserEvent(patientCreateRequest.getEmail(),
                activateToken
        ));
    }


}
