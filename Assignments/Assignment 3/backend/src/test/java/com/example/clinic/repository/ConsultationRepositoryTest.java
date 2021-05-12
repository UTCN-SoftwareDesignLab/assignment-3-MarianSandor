package com.example.clinic.repository;

import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.mapper.ConsultationMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.model.Consultation;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConsultationRepositoryTest {
    @Autowired
    private ConsultationRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ConsultationMapper consultationMapper;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        List<User> users = TestCreationFactory.listOf(User.class);
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);

        User user = users.get(0);
        Patient patient = patients.get(0);

        user = userRepository.save(user);
        patient = patientRepository.save(patient);

        Consultation consultationSaved = repository.save(Consultation.builder()
                .id(32L)
                .description("SADASDASD")
                .time(Calendar.getInstance())
                .user(user)
                .patient(patient)
                .build());

        assertNotNull(consultationSaved);
        System.out.println(consultationSaved);
        System.out.println(consultationMapper.toDTO(consultationSaved));
    }
}