package com.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Outbox  extends BaseEntity{
    private String aggregateType;
    private String aggregateId;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String payload;
}
