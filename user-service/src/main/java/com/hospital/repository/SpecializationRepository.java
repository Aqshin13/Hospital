package com.hospital.repository;

import com.hospital.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization,Long> {
}
