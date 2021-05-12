package com.example.clinic.repository.specifications;

import com.example.clinic.model.Consultation;
import com.example.clinic.model.ERole;
import com.example.clinic.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Calendar;

public class UserSpecifications {

    public static Specification<User> usernameLike(String username) {
        return (root, query, cb) -> cb.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<User> emailLike(String email) {
        return (root, query, cb) -> cb.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<User> userWithRole(ERole r) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            final Join<Object, Object> role = root.join("roles", JoinType.INNER);

            return criteriaBuilder.equal(role.get("name"), r);
        };
    }

    private static Subquery<Consultation> consultationSubquery(Root<User> root, CriteriaQuery<?> query,
                                                               CriteriaBuilder criteriaBuilder, Calendar calendar) {
        final Subquery<Consultation> consultationSubquery = query.subquery(Consultation.class);
        final Root<Consultation> fromConsultation = consultationSubquery.from(Consultation.class);
        return consultationSubquery.select(fromConsultation.get("user")).where(criteriaBuilder.and(
                criteriaBuilder.equal(fromConsultation.get("user"), root),
                criteriaBuilder.equal(fromConsultation.get("time"), calendar)
                )
        );
    }

    public static Specification<User> doctorsAvailableOn(Calendar calendar) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.not(root.in(consultationSubquery(root,query,criteriaBuilder,calendar)));
    }
}
