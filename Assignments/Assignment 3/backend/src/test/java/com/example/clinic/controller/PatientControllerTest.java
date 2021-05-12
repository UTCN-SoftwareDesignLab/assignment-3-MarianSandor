package com.example.clinic.controller;

import com.example.clinic.BaseControllerTest;
import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.service.ConsultationService;
import com.example.clinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static com.example.clinic.TestCreationFactory.randomLong;
import static com.example.clinic.TestCreationFactory.randomString;
import static com.example.clinic.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest extends BaseControllerTest {

    @InjectMocks
    private PatientController controller;

    @Mock
    private PatientService patientService;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new PatientController(patientService, consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allPatients() throws Exception {
        List<PatientDTO> patientDTOS = TestCreationFactory.listOf(PatientDTO.class);
        when(patientService.findAll()).thenReturn(patientDTOS);

        ResultActions response = mockMvc.perform(get(PATIENTS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(patientDTOS));
    }

    @Test
    void getPatient() throws Exception {
        long id = randomLong();
        PatientDTO reqPatient = PatientDTO.builder()
                .id(id)
                .address(randomString())
                .birthDate(new Date().toString())
                .cnp(randomString())
                .cardNumber(randomString())
                .build();
        when(patientService.get(id)).thenReturn(reqPatient);

        ResultActions result = performGetWithPathVariable(PATIENTS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqPatient));
    }

    @Test
    void getConsultationsForPatient() throws Exception {
        long id = randomLong();
        ConsultationDTO reqConsultation = ConsultationDTO.builder()
                .id(randomLong())
                .user_id(randomLong())
                .patient_id(id)
                .time(randomString())
                .build();
        when(consultationService.getConsultationsForPatient(id)).thenReturn(List.of(reqConsultation));

        ResultActions result = performGetWithPathVariable(PATIENTS + ENTITY + CONSULTATIONS, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(List.of(reqConsultation)));
    }

    @Test
    void create() throws Exception {
        PatientDTO reqPatient = PatientDTO.builder()
                .address(randomString())
                .birthDate(new Date().toString())
                .cnp(randomString())
                .cardNumber(randomString())
                .build();

        when(patientService.create(reqPatient)).thenReturn(reqPatient);

        ResultActions result = performPostWithRequestBody(PATIENTS, reqPatient);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqPatient));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        PatientDTO reqPatient = PatientDTO.builder()
                .id(id)
                .address(randomString())
                .birthDate(new Date().toString())
                .cnp(randomString())
                .cardNumber(randomString())
                .build();

        when(patientService.edit(id, reqPatient)).thenReturn(reqPatient);

        ResultActions result = performPutWithRequestBodyAndPathVariable(PATIENTS + ENTITY, reqPatient, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqPatient));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(patientService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(PATIENTS + ENTITY, id);
        verify(patientService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}