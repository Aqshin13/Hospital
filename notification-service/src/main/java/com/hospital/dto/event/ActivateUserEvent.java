package com.hospital.dto.event;

public record ActivateUserEvent(String email,String activateToken) {
}
