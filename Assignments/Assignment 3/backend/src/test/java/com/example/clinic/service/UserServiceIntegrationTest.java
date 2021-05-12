package com.example.clinic.service;

import com.example.clinic.dto.user.UserDTO;
import com.example.clinic.model.User;
import com.example.clinic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();

        userRepository.deleteAll();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            User u = userRepository.save(user);
        }

        List<UserDTO> userDTOS = userService.findAll();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userDTOS.get(i).getUsername());
        }
    }
}