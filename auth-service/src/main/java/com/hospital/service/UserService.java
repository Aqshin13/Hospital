package com.hospital.service;


import com.hospital.config.MailProducer;
import com.hospital.dto.event.ActivateUserEvent;
import com.hospital.dto.request.UserCreateRequest;
import com.hospital.entity.OutboxRepository;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public void saveDoctorUser(UserCreateRequest doctorCreateRequest) {
        userRepository.save(User.builder()
                .email(doctorCreateRequest.email())
                .role(User.Role.DOCTOR)
                .isActive(true)
                .isCompleted(false)
                .build());

    }

    public void savePatientUser(UserCreateRequest patientCreateRequest) {
        String activateToken = UUID.randomUUID().toString();
        userRepository.save(User.builder()
                .email(patientCreateRequest.email())
                .role(User.Role.PATIENT)
                .activateToken(activateToken)
                .isActive(false)
                .isCompleted(false)
                .build());
        mailProducer.sendActivate(new ActivateUserEvent(patientCreateRequest.email(),
                activateToken
        ));
    }


}
