package com.worthmate.demo.auth.controller;

import com.worthmate.demo.auth.request.SignupRequest;
import com.worthmate.demo.auth.request.AuthRequest;
import com.worthmate.demo.auth.service.AuthService;
import com.worthmate.demo.notification.service.EmailService;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.util.RoleEnum;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Optional role
        if (request.getRole() != null) {
            user.setRole(RoleEnum.valueOf(request.getRole().toUpperCase()));
        }

        // Save user
        UserEntity savedUser = authService.register(user);

        // Send welcome email
        emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());

        return "Signup successful! Please check your email.";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}