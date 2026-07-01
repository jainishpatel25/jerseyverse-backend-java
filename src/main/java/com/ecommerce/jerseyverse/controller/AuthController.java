package com.ecommerce.jerseyverse.controller;

import com.ecommerce.jerseyverse.dto.request.LoginRequestDto;
import com.ecommerce.jerseyverse.dto.request.RegisterRequestDto;
import com.ecommerce.jerseyverse.dto.response.LoginResponseDto;
import com.ecommerce.jerseyverse.dto.response.RegisterResponseDto;
import com.ecommerce.jerseyverse.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(
            @Valid @RequestBody RegisterRequestDto request) {

        RegisterResponseDto response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        LoginResponseDto response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}