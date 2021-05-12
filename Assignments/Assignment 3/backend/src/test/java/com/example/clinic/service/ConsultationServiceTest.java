package com.example.clinic.service;

import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.mapper.ConsultationMapper;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.mapper.UserMapper;
import com.example.clinic.model.Consultation;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import com.example.clinic.repository.ConsultationRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ConsultationServiceTest {
    @InjectMocks
    private ConsultationService consultationService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultationService = new ConsultationService(consultationRepository, patientRepository, userRepository, consultationMapper);
    }

    @Test
    void create() {
        PatientDTO patientDTO = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);
        Patient patient = (Patient) TestCreationFactory.listOf(Patient.class).get(0);
        patient.setId(patientDTO.getId());
        UserDTO userDTO = (UserDTO) TestCreationFactory.listOf(UserDTO.class).get(0);
        User user = (User) TestCreationFactory.listOf(User.class).get(0);
        user.setId(userDTO.getId());

        ConsultationDTO consultationDTO = ConsultationDTO.builder()
                                            .patient_id(patientDTO.getId())
                                            .user_id(userDTO.getId())
                                            .description("asdasdas")
                                            .time("17-10-2021 17:00")
                                            .build();
        Calendar calendar = TestCreationFactory.getCalendar("17-10-2021 17:00");

        Consultation consultation = Consultation.builder()
                .id(1L)
                .user(user)
                .patient(patient)
                .description("asdasdas")
                .time(calendar)
                .build();

        when(patientRepository.findById(patientDTO.getId())).thenReturn(Optional.of(patient));
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(user));
        when(consultationRepository.save(any())).thenReturn(consultation);
        when(consultationMapper.toDTO(any())).thenReturn(consultationDTO);

        ConsultationDTO consultationDTOSaved = consultationService.create(consultationDTO);
        System.out.println(consultationDTO);
        System.out.println(consultationDTOSaved);

        assertEquals(consultationDTO, consultationDTOSaved);
    }
}