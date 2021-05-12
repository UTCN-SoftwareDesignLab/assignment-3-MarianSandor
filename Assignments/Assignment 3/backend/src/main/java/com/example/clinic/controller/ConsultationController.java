package com.example.clinic.controller;

import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.clinic.UrlMapping.*;
import static com.example.clinic.UrlMapping.QUERY;

@RestController
@RequestMapping(value = API_PATH + CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationDTO> allConsultations() {
        return consultationService.findAll();
    }

    @GetMapping(ENTITY)
    public ConsultationDTO getConsultation(@PathVariable Long id) {
        return consultationService.get(id);
    }

    @PostMapping
    public ConsultationDTO create(@RequestBody ConsultationDTO consultationDTO) {
        return consultationService.create(consultationDTO);
    }

    @PutMapping(ENTITY)
    public ConsultationDTO edit(@PathVariable Long id, @RequestBody ConsultationDTO consultationDTO) {
        return consultationService.edit(id, consultationDTO);
    }

    @PatchMapping(ENTITY)
    public ConsultationDTO updateDescription(@PathVariable Long id, @RequestBody String description) {
        return consultationService.updateDescription(id, description);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        consultationService.delete(id);
    }
}
