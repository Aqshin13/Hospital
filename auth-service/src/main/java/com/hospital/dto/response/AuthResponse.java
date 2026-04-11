package com.hospital.dto.response;

import com.hospital.entity.User;

import java.util.UUID;

public record AuthResponse(String token, User.Role role, UUID id,String refreshToken) {

}
