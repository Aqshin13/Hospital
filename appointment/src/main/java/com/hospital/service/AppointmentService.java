package com.hospital.service;


import com.hospital.dto.request.AppointmentRequest;
import com.hospital.entity.Appointment;
import com.hospital.entity.DoctorSlot;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {


    private final AppointmentRepository appointmentRepository;
    private final DoctorSlotRepository doctorSlotRepository;



    public void createAppointment(UUID patientId, AppointmentRequest request){
        DoctorSlot doctorSlot=doctorSlotRepository.findById(request.id()).get();
        Appointment appointment= Appointment.builder()
                .notes(request.notes())
                .doctorSlot(doctorSlot)
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .patientUserId(patientId)
                .build();

        appointmentRepository.save(appointment);

    }

}
