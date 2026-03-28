package com.ecommerce.controller;

import com.ecommerce.common.Result;
import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Result<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return Result.success(authService.register(request), "Kayıt başarılı");
    }

    @PostMapping("/login")
    public Result<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request), "Giriş başarılı");
    }
}