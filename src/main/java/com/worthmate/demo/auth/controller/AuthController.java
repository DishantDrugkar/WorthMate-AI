package com.worthmate.demo.auth.controller;
import com.worthmate.demo.auth.request.AuthRequest;
import com.worthmate.demo.auth.service.AuthService;
import com.worthmate.demo.user.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String register(@RequestBody UserEntity user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest.getEmail(), authRequest.getPassword());
    }
}