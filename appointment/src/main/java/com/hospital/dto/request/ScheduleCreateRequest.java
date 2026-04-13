package com.hospital.dto.request;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleCreateRequest(
         LocalDate date, LocalTime workStart, LocalTime workEnd, Integer duration
) {
}
