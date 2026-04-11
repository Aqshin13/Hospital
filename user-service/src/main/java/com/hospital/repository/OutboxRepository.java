package com.hospital.repository;

import com.hospital.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox,Long> {
}
