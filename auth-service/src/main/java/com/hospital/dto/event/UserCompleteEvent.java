package com.hospital.dto.event;

import java.util.UUID;

public record UserCompleteEvent(UUID authId) {
}
