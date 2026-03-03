package com.worthmate.demo.auth.service;

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

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register user
    public UserEntity register(UserEntity user) {

        // Check if email exists
        if (userRepository.findByEmail(user.getEmail().toLowerCase()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setActive(true);

        // Default role = STUDENT
        if (user.getRole() == null) {
            user.setRole(RoleEnum.STUDENT);
        }

        return userRepository.save(user); // password now saved correctly
    }

    // Login user
    public String login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email.toLowerCase());

        if (user == null) {
            throw new RuntimeException("Invalid credentials: email not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials: wrong password");
        }

        // Generate JWT
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}