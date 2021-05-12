package com.example.clinic.repository;

import com.example.clinic.model.Consultation;
import com.example.clinic.model.ERole;
import com.example.clinic.model.Role;
import com.example.clinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>,  JpaSpecificationExecutor<Consultation> {
    Optional<List<Consultation>> findByUserId(Long id);

    Optional<List<Consultation>> findByPatientId(Long id);
}
