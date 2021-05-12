package com.example.clinic.service;

import com.example.clinic.dto.PatientDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void findAll() {
        int nrPatients = 10;
        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < nrPatients; i++) {
            Patient patient = Patient.builder()
                    .name("Patient" + i)
                    .address("Address" + i)
                    .cardNumber("cn" + i)
                    .cnp("cnp" + i)
                    .birthDate(new Date())
                    .build();
            patients.add(patient);
            patientRepository.save(patient);
        }

        List<PatientDTO> patientDTOS = patientService.findAll();

        for (int i = 0; i < nrPatients; i++) {
            assertEquals(patients.get(i).getId(), patientDTOS.get(i).getId());
            assertEquals(patients.get(i).getName(), patientDTOS.get(i).getName());
        }
    }
}
