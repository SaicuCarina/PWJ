package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.AuthDTO;
import com.example.proiect_pwj.dto.UserDTO;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserSession userSession;

    @InjectMocks private UserService userService;

    @Test
    void whenRegister_thenUserIsSaved() {
        UserDTO dto = new UserDTO();
        dto.setEmail("nou@gmail.com");
        dto.setFullName("Test User");
        dto.setPassword("parola123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User savedUser = userService.register(dto);

        assertNotNull(savedUser);
        assertEquals("nou@gmail.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void whenLoginWithWrongPassword_thenThrowException() {
        User existingUser = new User();
        existingUser.setEmail("test@gmail.com");
        existingUser.setPassword("corect");

        AuthDTO auth = new AuthDTO();
        auth.setEmail("test@gmail.com");
        auth.setPassword("greșit");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(existingUser));

        assertThrows(RuntimeException.class, () -> userService.login(auth));
    }
}