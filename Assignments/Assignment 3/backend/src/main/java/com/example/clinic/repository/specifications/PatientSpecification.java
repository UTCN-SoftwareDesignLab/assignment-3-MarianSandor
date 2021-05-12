package com.example.clinic.repository.specifications;

import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {

    public static Specification<Patient> nameLike(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }
}
