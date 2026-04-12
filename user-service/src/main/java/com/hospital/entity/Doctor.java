package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
