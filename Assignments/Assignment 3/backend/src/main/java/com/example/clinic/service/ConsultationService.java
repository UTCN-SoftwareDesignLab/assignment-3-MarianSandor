package com.example.clinic.service;

import com.example.clinic.dto.ConsultationDTO;
import com.example.clinic.mapper.ConsultationMapper;
import com.example.clinic.model.Consultation;
import com.example.clinic.model.Patient;
import com.example.clinic.model.User;
import com.example.clinic.repository.ConsultationRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.UserRepository;
import com.example.clinic.repository.specifications.ConsultationSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.clinic.repository.specifications.ConsultationSpecifications.*;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ConsultationMapper consultationMapper;

    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }

    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll()
                .stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());

    }

    public List<ConsultationDTO> getConsultationsForPatient(Long id) {
       return consultationRepository.findByPatientId(id).orElseThrow(() -> new EntityNotFoundException("Consultation not found for patient_id: " + id)).stream()
               .map(consultationMapper::toDTO)
               .collect(Collectors.toList());
    }

    public List<ConsultationDTO> getConsultationsForUser(Long id) {
        return consultationRepository.findByUserId(id).orElseThrow(() -> new EntityNotFoundException("Consultation not found for user_id: " + id)).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConsultationDTO> getPastConsultationsForUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return consultationRepository.findAll(consultationsByUser(user).and(consultationsBefore(Calendar.getInstance()))).stream()
                .map(consultationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ConsultationDTO create(ConsultationDTO consultationDTO) {
        User user = userRepository.findById(consultationDTO.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + consultationDTO.getUser_id()));
        Patient patient = patientRepository.findById(consultationDTO.getPatient_id())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + consultationDTO.getUser_id()));

        Consultation consultation = Consultation.builder()
                .user(user)
                .patient(patient)
                .description(consultationDTO.getDescription())
                .time(getCalendarTime(consultationDTO.getTime()))
                .build();

        return consultationMapper.toDTO(consultationRepository.save(consultation));
    }

    public ConsultationDTO edit(Long id, ConsultationDTO consultationDTO) {
        Consultation currConsultation = findById(id);

        currConsultation.setDescription(consultationDTO.getDescription());
        currConsultation.setTime(getCalendarTime(consultationDTO.getTime()));

        return consultationMapper.toDTO(consultationRepository.save(currConsultation));
    }

    public ConsultationDTO updateDescription(Long id, String description) {
        Consultation currConsultation = findById(id);

        currConsultation.setDescription(description);

        return consultationMapper.toDTO(consultationRepository.save(currConsultation));
    }

    public ConsultationDTO get(Long id) {
        return consultationMapper.toDTO(findById(id));
    }

    public void delete(Long id) {
        consultationRepository.deleteById(id);
    }

    public static Calendar getCalendarTime(String time) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        calendar.setTime(date);

        return calendar;
    }
}
