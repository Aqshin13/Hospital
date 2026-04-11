package com.hospital.service;


import com.hospital.dto.request.Credentials;
import com.hospital.dto.response.AuthResponse;
import com.hospital.dto.response.RefreshTokenResponse;
import com.hospital.entity.RefreshToken;
import com.hospital.entity.User;
import com.hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

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
        String storedOtp = redisTemplate.opsForValue().get(otpKey);

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
        RefreshToken refreshToken = refreshTokenService.findByUser(user.get());
        if (refreshToken == null) {
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .user(user.get())
                    .expireDate(Date
                            .from(Instant
                                    .now()
                                    .plus(7, ChronoUnit.DAYS)))
                    .build();
        }
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(Date
                .from(Instant
                        .now()
                        .plus(7, ChronoUnit.DAYS)));
        RefreshToken refTokenDB = refreshTokenService.save(refreshToken);
        String token = jwtTokenService.createToken(user.get());
        redisTemplate.delete(getOtpKey(credentials.email()));
        return new AuthResponse(token, user.get().getRole(),
                user.get().getId(),
                refTokenDB.getRefreshToken());


    }


    public AuthResponse activateUser(String activationToken) {
        Optional<User> user = userRepository.findByActivateToken(activationToken);
        if (user.isEmpty()) {
            throw new RuntimeException("Invalid request");
        }
        User userDB = user.get();
        userDB.setActive(true);
        userDB.setActivateToken(null);
        userRepository.save(userDB);
        RefreshToken refreshToken = refreshTokenService.findByUser(user.get());
        if (refreshToken == null) {
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .user(user.get())
                    .expireDate(Date
                            .from(Instant
                                    .now()
                                    .plus(7, ChronoUnit.DAYS)))
                    .build();
        }
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(Date
                .from(Instant
                        .now()
                        .plus(7, ChronoUnit.DAYS)));
        RefreshToken refTokenDB = refreshTokenService.save(refreshToken);
        String token = jwtTokenService.createToken(userDB);
        return new AuthResponse(token,
                userDB.getRole(),
                userDB.getId(),
                refTokenDB.getRefreshToken());
    }


    public RefreshTokenResponse refreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenDB =
                refreshTokenService
                        .findByRefreshToken(refreshToken);
        User user = refreshTokenDB.get().getUser();
        return new RefreshTokenResponse(jwtTokenService.createToken(user));

    }


}
