package com.example.clinic.repository.specifications;

import com.example.clinic.model.Consultation;
import com.example.clinic.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;

public class ConsultationSpecifications {
    public static Specification<Consultation> consultationsBefore(Calendar calendar) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("time"), calendar);
    }

    public static Specification<Consultation> consultationsByUser(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
