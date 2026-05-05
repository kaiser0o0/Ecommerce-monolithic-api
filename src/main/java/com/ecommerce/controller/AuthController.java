package com.ecommerce.controller;

import com.ecommerce.common.Result;
import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.dto.auth.TokenRequest;
import com.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Result<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        Result<AuthResponse> result = authService.register(request);
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Result<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        Result<AuthResponse> result = authService.login(request);
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.status(401).body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Result<Void>> logout(@RequestBody TokenRequest request) {
        Result<Void> result = authService.logout(request.getToken());
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}