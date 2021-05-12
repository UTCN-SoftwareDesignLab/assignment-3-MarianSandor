package com.example.clinic;

import com.example.clinic.dto.request.SignupRequest;
import com.example.clinic.model.*;
import com.example.clinic.repository.ConsultationRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.repository.RoleRepository;
import com.example.clinic.repository.UserRepository;
import com.example.clinic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static com.example.clinic.model.ERole.DOCTOR;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PatientRepository patientRepository;

    private final ConsultationRepository consultationRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            patientRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            consultationRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("marian@email.com")
                    .username("marian")
                    .password("123")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("marianS@email.com")
                    .username("marianS")
                    .password("321")
                    .roles(Set.of("SECRETARY"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("marianD@email.com")
                    .username("marianD")
                    .password("321")
                    .roles(Set.of("DOCTOR"))
                    .build());
            Patient patientA = patientRepository.save(Patient.builder()
                    .cnp("6210504019531")
                    .cardNumber("5425233430109903")
                    .name("PatientA")
                    .address("A city")
                    .birthDate(new Date())
                    .build());
            Patient patientB = patientRepository.save(Patient.builder()
                    .cnp("5210504018060")
                    .cardNumber("2222420000001113")
                    .name("PatientB")
                    .address("Another city")
                    .birthDate(new Date())
                    .build());

            User doctor = userRepository.findByUsername("marianD").orElse(User.builder()
                    .username("Doctor")
                    .email("doctor@gmail.com")
                    .password("doctor")
                    .build());

            consultationRepository.save(Consultation.builder()
                    .patient(patientA)
                    .user(doctor)
                    .time(Calendar.getInstance())
                    .description("Esti bine")
                    .build());
            consultationRepository.save(Consultation.builder()
                    .patient(patientB)
                    .user(doctor)
                    .time(Calendar.getInstance())
                    .description("Ii gata de el..")
                    .build());
        }
    }
}
