package com.hospital.service;


import com.hospital.config.MailProducer;
import com.hospital.dto.event.ActivateUserEvent;
import com.hospital.dto.request.DoctorCreateRequest;
import com.hospital.dto.request.PatientCreateRequest;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailProducer mailProducer;

    public void saveDoctorUser(DoctorCreateRequest doctorCreateRequest) {
        userRepository.save(User.builder()
                .email(doctorCreateRequest.getEmail())
                .role(User.Role.DOCTOR)
                .isActive(true)
                .build());
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
