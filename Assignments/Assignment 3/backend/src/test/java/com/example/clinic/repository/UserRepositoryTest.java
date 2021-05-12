package com.example.clinic.repository;

import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.model.Consultation;
import com.example.clinic.model.Patient;
import com.example.clinic.model.Role;
import com.example.clinic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static com.example.clinic.model.ERole.DOCTOR;
import static com.example.clinic.repository.specifications.UserSpecifications.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void beforeEach() {
        consultationRepository.deleteAll();
        repository.deleteAll();
        roleRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    public void testMock() {
        User userSaved = repository.save(User.builder()
                .username("asdasd")
                .email("dasdas@gmail.com")
                .password("aaaaa")
                .build());

        assertNotNull(userSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(User.builder().build());
        });
    }

    @Test
    public void testSearch() {
        User user1 = User.builder()
                .username("marian")
                .email("marian@gmail.com")
                .password("aaaaa")
                .build();

        User user2 = User.builder()
                .username("altMarian")
                .email("marian4322@gmail.com")
                .password("aaaaa")
                .build();

        User user3 = User.builder()
                .username("noname")
                .email("noname@yahoo.com")
                .password("aaaaa")
                .build();

        repository.save(user1);
        repository.save(user2);
        repository.save(user3);

        String username = "%arian%";
        List<User> users = repository.findAll(usernameLike(username));

        assertEquals(2, users.size());

        String email = "%@yahoo.com";
        users = repository.findAll(usernameLike(username).or(emailLike(email)));

        assertEquals(3, users.size());
    }

    @Test
    public void availableDoctors() {
        Role role = Role.builder()
                .name(DOCTOR)
                .build();
        role = roleRepository.save(role);

        User user1 = User.builder()
                .username("marian")
                .email("marian@gmail.com")
                .password("aaaaa")
                .roles(Set.of(role))
                .build();
        User user2 = User.builder()
                .username("altMarian")
                .email("marian4322@gmail.com")
                .password("aaaaa")
                .roles(Set.of(role))
                .build();
        User user3 = User.builder()
                .username("noname")
                .email("noname@yahoo.com")
                .password("aaaaa")
                .roles(Set.of(role))
                .build();
        User user4 = User.builder()
                .username("DefinetlyNotADoctor")
                .email("DefinetlyNotADoctor@yahoo.com")
                .password("aaaaa")
                .build();
        user1 = repository.save(user1);
        user2 = repository.save(user2);
        user3 = repository.save(user3);
        repository.save(user4);

        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        patient = patientRepository.save(patient);

        Calendar calendar1 = TestCreationFactory.getCalendar("17-10-2021 17:00");
        Calendar calendar2 = TestCreationFactory.getCalendar("17-10-2021 18:00");

        Consultation consultation1 = Consultation.builder()
                .user(user1)
                .patient(patient)
                .time(calendar1)
                .build();
        Consultation consultation2 = Consultation.builder()
                .user(user2)
                .patient(patient)
                .time(calendar2)
                .build();
        Consultation consultation3 = Consultation.builder()
                .user(user3)
                .patient(patient)
                .time(calendar1)
                .build();

        consultationRepository.save(consultation1);
        consultationRepository.save(consultation2);
        consultationRepository.save(consultation3);

        List<User> users = repository.findAll(userWithRole(DOCTOR).and(doctorsAvailableOn(calendar1)));
        assertEquals(List.of(user2), users);

        users = repository.findAll(userWithRole(DOCTOR).and(doctorsAvailableOn(calendar2)));
        assertEquals(List.of(user1, user3), users);

        Calendar calendar = TestCreationFactory.getCalendar("17-11-2021 18:00");
        users = repository.findAll(userWithRole(DOCTOR).and(doctorsAvailableOn(calendar)));
        assertEquals(List.of(user1, user2, user3), users);
    }
}