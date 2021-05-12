package com.example.clinic.service;

import com.example.clinic.dto.PatientDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.mapper.PatientMapper;
import com.example.clinic.model.Patient;
import com.example.clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.clinic.repository.specifications.PatientSpecification.nameLike;
import static com.example.clinic.repository.specifications.UserSpecifications.emailLike;
import static com.example.clinic.repository.specifications.UserSpecifications.usernameLike;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> search(String query) {
        return patientRepository.findAll(nameLike(query)).stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patientDTO) {
        return patientMapper.toDTO(patientRepository.save(
                patientMapper.fromDTO(patientDTO)
        ));
    }

    public PatientDTO edit(Long id, PatientDTO patientDTO) {
        Patient currPatient = findById(id);
        Patient patient = patientMapper.fromDTO(patientDTO);

        currPatient.setAddress(patient.getAddress());
        currPatient.setCnp(patient.getCnp());
        currPatient.setCardNumber(patient.getCardNumber());
        currPatient.setName(patient.getName());
        currPatient.setBirthDate(patient.getBirthDate());

        return patientMapper.toDTO(patientRepository.save(currPatient));
    }

    public PatientDTO get(Long id) {
        return patientMapper.toDTO(findById(id));
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
