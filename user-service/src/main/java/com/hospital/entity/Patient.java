package com.hospital.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Patient extends BaseUserEntity {


    private String address;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
    @Enumerated(EnumType.STRING)
    private Gender gender;



    @Getter
    public enum BloodGroup {
        A_POSITIVE("A+"),
        A_NEGATIVE("A-"),
        B_POSITIVE("B+"),
        B_NEGATIVE("B-"),
        AB_POSITIVE("AB+"),
        AB_NEGATIVE("AB-"),
        O_POSITIVE("O+"),
        O_NEGATIVE("O-");

        private final String label;

        BloodGroup(String label) {
            this.label = label;
        }

    }

}
