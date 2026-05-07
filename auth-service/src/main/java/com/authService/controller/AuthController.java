package com.authService.controller;

import com.authService.dto.LoginRequest;
import com.authService.dto.RegisterRequest;
import com.authService.entity.AuthUser;
import com.authService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<AuthUser> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(service.register(request));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(service.login(request));
    }
}