package com.example.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String name;

    //@CreditCardNumber
    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 13)
    private String cnp;

    @Column(nullable = false, length = 20)
    private String address;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
}
