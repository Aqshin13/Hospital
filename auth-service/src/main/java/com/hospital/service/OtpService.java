package com.hospital.service;


import com.hospital.config.MailProducer;
import com.hospital.dto.event.OtpEvent;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final  SecureRandom random = new SecureRandom();
    private static final Duration OTP_TTL = Duration.ofMinutes(5);
    private final MailProducer mailProducer;
    private final RedisTemplate<String,String> redisTemplate;
    private final UserRepository userRepository;

    public void sendOtp(String email){
        Optional<User> user= userRepository.findByEmail(email);
        if(user.isEmpty() || !user.get().isActive()){
            throw new RuntimeException("User is not found");
        }
        String otp=generate4DigitOtp();
        String otpKey = getOtpKey(email);
        String attemptKey = getAttemptKey(email);
        redisTemplate.opsForValue().set(otpKey, otp, OTP_TTL);
        redisTemplate.opsForHash().put(attemptKey, email, "0");
        redisTemplate.expire(attemptKey, OTP_TTL);
        mailProducer.sendOtp(new OtpEvent(email,otp));
    }

    private  String generate4DigitOtp() {
        int otp = 1000 + random.nextInt(9000); // 1000-9999
        return String.valueOf(otp);
    }

    private String getOtpKey(String email) {
        return "otp:" + email;
    }

    private String getAttemptKey(String email) {
        return "otp:attempt:" + email;
    }

    private String getBlockKey(String email) {
        return "otp:block:" + email;
    }

}
