package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Department extends BaseEntity{

private String name;
@OneToMany(mappedBy = "department")
private List<Doctor> doctors;

}

