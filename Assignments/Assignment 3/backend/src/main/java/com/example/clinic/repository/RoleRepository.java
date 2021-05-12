package com.example.clinic.repository;

import com.example.clinic.model.ERole;
import com.example.clinic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole role);

}
