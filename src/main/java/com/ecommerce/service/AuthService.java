package com.ecommerce.service;

import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmailAndIsDeletedFalse(request.getEmail()).isPresent()) {
            throw new BusinessException("Email zaten kullanımda");
        }
        User user = userMapper.toEntity(request, passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return new AuthResponse(jwt, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Hatalı şifre");
        }

        String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return new AuthResponse(jwt, refreshToken);
    }
}