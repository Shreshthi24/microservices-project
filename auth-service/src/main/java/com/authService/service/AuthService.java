package com.authService.service;

import com.authService.dto.*;
import com.authService.entity.AuthUser;

import com.authService.kafka.AuthKafkaProducer;
import com.authService.repositories.AuthRepository;
import com.authService.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthKafkaProducer kafkaProducer;

    // REGISTER
    public AuthUser register(RegisterRequest request) {

        AuthUser user = new AuthUser();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        return repo.save(user);
    }

    // LOGIN
    public String login(LoginRequest request) {

        AuthUser user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {

            // LOGIN FAILED EVENT
            String failEvent = user.getEmail() + "|FAILED|LOGIN";
            kafkaProducer.sendAuthEvent(failEvent);

            throw new RuntimeException("Invalid credentials");
        }

        // LOGIN SUCCESS EVENT
        String successEvent = user.getEmail() + "|SUCCESS|LOGIN";
        kafkaProducer.sendAuthEvent(successEvent);

        // GENERATE TOKEN
        return jwtUtil.generateToken(user.getEmail());
    }
}