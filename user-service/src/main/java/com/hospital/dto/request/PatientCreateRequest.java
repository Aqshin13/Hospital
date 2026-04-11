package com.hospital.dto.request;


import com.hospital.entity.Gender;
import com.hospital.entity.Patient;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientCreateRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private Patient.BloodGroup bloodGroup;
    private Gender gender;
}
