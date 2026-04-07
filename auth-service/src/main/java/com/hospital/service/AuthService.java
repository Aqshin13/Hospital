package com.hospital.service;


import com.hospital.dto.request.Credentials;
import com.hospital.dto.response.AuthResponse;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenService jwtTokenService;

    private String getAttemptKey(String email) {
        return "otp:attempt:" + email;
    }

    public void incrementAttempt(String email) {
        String attemptKey = getAttemptKey(email);
        redisTemplate.opsForHash().increment(attemptKey, email, 1);
    }

    public int getAttemptCount(String email) {
        String attemptKey = getAttemptKey(email);
        Object value = redisTemplate.opsForHash().get(attemptKey, email);
        return value != null ? Integer.parseInt(value.toString()) : 0;
    }

    private String getOtpKey(String email) {
        return "otp:" + email;
    }

    private String getBlockKey(String email) {
        return "block:" + email;
    }

    public void checkIfBlocked(String email) {
        String blockKey = getBlockKey(email);
        Boolean isBlocked = redisTemplate.hasKey(blockKey);
        if (Boolean.TRUE.equals(isBlocked)) {
            throw new RuntimeException("Blokdasiz");
        }
    }

    public boolean verifyOtp(String email, String inputOtp) {
        checkIfBlocked(email);
        if (getAttemptCount(email) >= 3) {
            redisTemplate.opsForValue().set(getBlockKey(email),
                    "blocked",
                    15, TimeUnit.MINUTES);
            redisTemplate.delete(getAttemptKey(email));
            throw new RuntimeException("Çox sayda yanlış cəhd. 15 dəqiqə bloklandınız.");

        }
        String otpKey = getOtpKey(email);
        String storedOtp = (String) redisTemplate.opsForValue().get(otpKey);

        if (storedOtp == null || !storedOtp.equals(inputOtp)) {
            incrementAttempt(email);
            return false;
        }

        return true;
    }

    public AuthResponse login(Credentials credentials) {
        checkIfBlocked(credentials.email());

        Optional<User> user = userRepository
                .findByEmail(credentials.email());
        if (user.isEmpty()) {
            throw new RuntimeException("User is not found");
        }
        if (!verifyOtp(credentials.email(), credentials.otp())) {
            throw new RuntimeException("Invalid otp");
        }
        String token = jwtTokenService.createToken(user.get());
        return new AuthResponse(token, user.get().getRole());


    }


}
