package com.hospital.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PatientCreateRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
