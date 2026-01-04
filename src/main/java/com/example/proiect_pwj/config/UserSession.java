package com.example.proiect_pwj.config;

import com.example.proiect_pwj.model.User;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserSession {
    private final Map<String, User> activeSessions = new HashMap<>();

    public void addSession(String token, User user) {
        activeSessions.put(token, user);
    }

    public User getUser(String token) {
        return activeSessions.get(token);
    }
}