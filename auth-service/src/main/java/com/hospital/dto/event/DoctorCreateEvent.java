package com.hospital.dto.event;


import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorCreateEvent {
    private String firstName;
    private String lastName;
    private UUID userId;
}
