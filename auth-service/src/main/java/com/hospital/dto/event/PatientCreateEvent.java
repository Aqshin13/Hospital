package com.hospital.dto.event;


import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatientCreateEvent {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String phone;

}
