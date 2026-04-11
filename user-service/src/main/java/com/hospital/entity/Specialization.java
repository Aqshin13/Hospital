package com.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Specialization extends BaseEntity {

    private String name;
    @OneToMany(mappedBy = "specialization")
    private List<Doctor> doctor;
}
