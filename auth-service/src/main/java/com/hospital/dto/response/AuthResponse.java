package com.hospital.dto.response;

import com.hospital.entity.User;

public record AuthResponse(String token, User.Role role) {

}
