package com.worthmate.demo.auth.service;

import com.worthmate.demo.auth.request.AuthRequest;
import com.worthmate.demo.auth.response.AuthResponse;
import com.worthmate.demo.config.JwtUtil;
import com.worthmate.demo.user.entity.UserEntity;
import com.worthmate.demo.user.repository.UserRepository;
import com.worthmate.demo.util.RoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        //Encode Password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Make user active
        user.setActive(true);

        // Set default role (STUDENT for example)
        if (user.getRole() == null) {
            user.setRole(RoleEnum.STUDENT); // ya jo bhi enum tumhare UserEntity me hai
        }
        userRepository.save(user);
        return "Registration successful";
    }

    public String login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}