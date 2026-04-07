package com.hospital.config;

import com.hospital.dto.event.ActivateUserEvent;
import com.hospital.dto.event.OtpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailProducer {


    private final RabbitTemplate template;


    public void sendOtp(OtpEvent otp){
        template.convertAndSend("exchange",
                "mailotp-rounting-key",
                otp);
    }

    public void sendActivate(ActivateUserEvent userEvent){
        template.convertAndSend("exchange",
                "mailactivate-rounting-key",
                userEvent);
    }

}
