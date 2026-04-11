package com.hospital.dto.request;


import com.hospital.entity.Department;
import com.hospital.entity.Specialization;
import jakarta.persistence.ManyToOne;
import lombok.*;
import tools.jackson.databind.annotation.JsonDeserialize;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorCreateRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private Integer experienceYears;
    private Integer departmentId;
    private Integer specializationId;

}
