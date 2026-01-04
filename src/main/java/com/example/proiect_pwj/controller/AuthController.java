package com.example.proiect_pwj.controller;

import com.example.proiect_pwj.dto.AuthDTO;
import com.example.proiect_pwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody AuthDTO authDTO) {
        return userService.login(authDTO);
    }
}