package com.example.proiect_pwj.service;

import com.example.proiect_pwj.config.UserSession;
import com.example.proiect_pwj.dto.AuthDTO;
import com.example.proiect_pwj.dto.UserDTO;
import com.example.proiect_pwj.model.User;
import com.example.proiect_pwj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserSession userSession;

    public String login(AuthDTO authDTO) {
        User user = userRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Email inexistent"));

        if (!user.getPassword().equals(authDTO.getPassword())) {
            throw new RuntimeException("Parola incorecta");
        }

        String token = UUID.randomUUID().toString();
        userSession.addSession(token, user);
        return token;
    }

    public User register(UserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Acest email este deja inregistrat!");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return userRepository.save(user);
    }
}