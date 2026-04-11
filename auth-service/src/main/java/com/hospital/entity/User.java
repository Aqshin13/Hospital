package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseEntity {


    private String email;
    private boolean isActive=false;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String activateToken;
    private boolean isCompleted;



    public static enum Role{
        PATIENT,DOCTOR,ADMIN
    }






}
