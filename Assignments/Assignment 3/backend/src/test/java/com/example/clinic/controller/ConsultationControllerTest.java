package com.example.clinic.controller;

import com.example.clinic.BaseControllerTest;
import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.service.ConsultationService;
import com.example.clinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.clinic.TestCreationFactory.randomLong;
import static com.example.clinic.TestCreationFactory.randomString;
import static com.example.clinic.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultationControllerTest extends BaseControllerTest {

    @InjectMocks
    private ConsultationController controller;

    @Mock
    private ConsultationService consultationService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ConsultationController(consultationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allConsultations() throws Exception {
        List<ConsultationDTO> consultationDTOS = TestCreationFactory.listOf(ConsultationDTO.class);
        when(consultationService.findAll()).thenReturn(consultationDTOS);

        ResultActions response = mockMvc.perform(get(API_PATH + CONSULTATIONS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(consultationDTOS));

    }

    @Test
    void getConsultation() throws Exception {
        long id = randomLong();
        ConsultationDTO reqConsultation = ConsultationDTO.builder()
                .id(id)
                .description(randomString())
                .user_id(randomLong())
                .patient_id(randomLong())
                .time("2020-05-12 00:00")
                .build();
        when(consultationService.get(id)).thenReturn(reqConsultation);

        ResultActions result = performGetWithPathVariable(API_PATH + CONSULTATIONS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqConsultation));
    }

    @Test
    void create() throws Exception {
        ConsultationDTO reqConsultation = ConsultationDTO.builder()
                .description(randomString())
                .user_id(randomLong())
                .patient_id(randomLong())
                .time("2020-05-12 00:00")
                .build();

        when(consultationService.create(reqConsultation)).thenReturn(reqConsultation);

        ResultActions result = performPostWithRequestBody(API_PATH + CONSULTATIONS, reqConsultation);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqConsultation));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        ConsultationDTO reqConsultation = ConsultationDTO.builder()
                .id(id)
                .description(randomString())
                .user_id(randomLong())
                .patient_id(randomLong())
                .time("2020-05-12 00:00")
                .build();

        when(consultationService.edit(id, reqConsultation)).thenReturn(reqConsultation);

        ResultActions result = performPutWithRequestBodyAndPathVariable(API_PATH + CONSULTATIONS + ENTITY, reqConsultation, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqConsultation));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(consultationService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(API_PATH + CONSULTATIONS + ENTITY, id);
        verify(consultationService, times(1)).delete(id);

        result.andExpect(status().isOk());
    }
}