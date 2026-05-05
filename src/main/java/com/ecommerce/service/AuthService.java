package com.ecommerce.service;
import com.ecommerce.common.Result;
import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.RegisterRequest;

public interface AuthService {
    Result<AuthResponse> register(RegisterRequest request);
    Result<AuthResponse> login(LoginRequest request);
    Result<Void> logout(String token);
}