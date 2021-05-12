package com.example.clinic.service;

import com.example.clinic.dto.DoctorDTO;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.UserMapper;
import com.example.clinic.model.Role;
import com.example.clinic.model.User;
import com.example.clinic.repository.RoleRepository;
import com.example.clinic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.clinic.model.ERole.DOCTOR;
import static com.example.clinic.repository.specifications.UserSpecifications.*;
import static com.example.clinic.repository.specifications.UserSpecifications.doctorsAvailableOn;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final DoctorMapper doctorMapper;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserDTO get(Long id) {
        return userMapper.toDTO(findById(id));
    }

    public List<UserDTO> findAll() {
        List<User> us = userRepository.findAll();
        userMapper.toDTO(us.get(0));
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> search(String query) {
        return userRepository.findAll(usernameLike(query).or(emailLike(query))).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<List<DoctorDTO>> getDoctorsAvailable(String query) {
        if (isDateTime(query)) {
            Calendar calendar = ConsultationService.getCalendarTime(query);
            return List.of(userRepository.findAll(userWithRole(DOCTOR).and(doctorsAvailableOn(calendar))).stream()
                        .map(doctorMapper::toDTO)
                        .collect(Collectors.toList()));
        } else if (isDate(query)) {
            List<List<DoctorDTO>> doctorsAvailablePerHour = new ArrayList<>();
            Calendar calendar = ConsultationService.getCalendarTime(query + " 00:00");

            for (int i = 0; i < 24; i++) {
                List<DoctorDTO> doctors= userRepository.findAll(userWithRole(DOCTOR).and(doctorsAvailableOn(calendar))).stream()
                        .map(doctorMapper::toDTO)
                        .collect(Collectors.toList());
                doctorsAvailablePerHour.add(i, doctors);

                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }

            return doctorsAvailablePerHour;
        }

        return null;
    }

    public UserDTO create(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(encoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();

        Role role = roleRepository.findByName(userDTO.getRole()).orElseThrow(() -> new RuntimeException("Cannot find role: " + userDTO.getRole()));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);

        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO edit(Long id, UserDTO user) {
        User currUser = findById(id);

        currUser.setEmail(user.getEmail());
        currUser.setUsername(user.getUsername());

        Role role = roleRepository.findByName(user.getRole()).orElseThrow(() -> new RuntimeException("Cannot find role: " + user.getRole()));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        currUser.setRoles(roles);

        return userMapper.toDTO(
                userRepository.save(currUser)
        );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private boolean isDateTime(String query) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            sdf.parse(query);
            return true;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean isDate(String query) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            sdf.parse(query);
            return true;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
