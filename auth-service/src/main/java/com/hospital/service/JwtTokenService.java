package com.hospital.service;


import com.hospital.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());
    private final ObjectMapper mapper;

    public String createToken(User user) {
        TokenSubject tokenSubject = new TokenSubject(user.getId(),
                user.isActive()
                , user.getRole()
                ,user.isCompleted()
        );
        try {
            String subject = mapper.writeValueAsString(tokenSubject);
            return Jwts.builder().setSubject(subject)
                    .setExpiration(Date
                            .from(Instant
                                    .now()
                                    .plus(10, ChronoUnit.MINUTES)))
                    .signWith(key).compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static record TokenSubject(UUID id,
                                      boolean active,
                                      User.Role role,
                                      boolean isCompleted) {
    }

}
