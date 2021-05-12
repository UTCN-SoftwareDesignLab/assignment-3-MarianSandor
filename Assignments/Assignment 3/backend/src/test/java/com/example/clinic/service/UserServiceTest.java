package com.example.clinic.service;

import com.example.clinic.TestCreationFactory;
import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.UserMapper;
import com.example.clinic.model.User;
import com.example.clinic.repository.RoleRepository;
import com.example.clinic.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private DoctorMapper doctorMapper;


    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(passwordEncoder, userRepository, roleRepository, userMapper, doctorMapper);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> all = userService.findAll();

        Assertions.assertEquals(users.size(), all.size());
    }
}