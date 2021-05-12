package com.example.clinic.controller;

import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.dto.PatientDTO;
import com.example.clinic.service.ConsultationService;
import com.example.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.clinic.UrlMapping.*;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final ConsultationService consultationService;

    @GetMapping
    public List<PatientDTO> allPatients() {
        return patientService.findAll();
    }

    @GetMapping(ENTITY)
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.get(id);
    }

    @GetMapping(SEARCH + QUERY)
    public List<PatientDTO> allPatientsBy(@PathVariable String query){
        return patientService.search(query);
    }

    @GetMapping(ENTITY + CONSULTATIONS)
    public List<ConsultationDTO> getConsultationsForPatient(@PathVariable Long id) {
        return consultationService.getConsultationsForPatient(id);
    }

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patientDTO) {
        return patientService.create(patientDTO);
    }

    @PutMapping(ENTITY)
    public PatientDTO edit(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        return patientService.edit(id, patientDTO);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        patientService.delete(id);
    }
}
