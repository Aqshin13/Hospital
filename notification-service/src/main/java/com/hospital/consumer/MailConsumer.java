package com.hospital.consumer;


import com.hospital.dto.event.ActivateUserEvent;
import com.hospital.dto.event.OtpEvent;
import com.hospital.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailConsumer {

    private final EmailService service;


    @RabbitListener(queues = {"activate-mail"})
    public void consumeActivateMessages(ActivateUserEvent event) {
        service.sendActivationEmail(event.email(), event.activateToken());
    }

    @RabbitListener(queues = {"otp-mail"})
    public void consumeAOtpMessage(OtpEvent event) {
        service.sendOtpMail(event.mail(), event.otp());
    }



}
