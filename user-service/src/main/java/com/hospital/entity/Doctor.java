package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Doctor extends BaseUserEntity{

    private Integer experienceYears;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Specialization specialization;


}
