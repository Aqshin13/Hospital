package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {


    private String email;
    private boolean isActive=false;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String activateToken;



    public static enum Role{
        PATIENT,DOCTOR,ADMIN
    }



}
