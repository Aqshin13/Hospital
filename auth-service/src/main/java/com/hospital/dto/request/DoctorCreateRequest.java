package com.hospital.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DoctorCreateRequest {

    private String email;
    private String firstName;
    private String lastName;

}
