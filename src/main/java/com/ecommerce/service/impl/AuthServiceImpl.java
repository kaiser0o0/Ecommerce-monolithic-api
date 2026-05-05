package com.ecommerce.service.impl;

import com.ecommerce.common.Result;
import com.ecommerce.dto.auth.AuthResponse;
import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.entity.User;
import com.ecommerce.enums.Role; // Role importu eklendi
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.RefreshTokenService;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class
AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Result<AuthResponse> register(RegisterRequest request) {
        if (userService.getUserByEmail(request.getEmail()).isSuccess()) {
            return Result.error("Email zaten kullanımda");
        }

        User user = userMapper.toEntity(request, passwordEncoder.encode(request.getPassword()));

        // HATA 2 ÇÖZÜMÜ: Eğer istekten rol gelmezse NPE almamak için varsayılan rol ata
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // HATA 1 ÇÖZÜMÜ: Kayıt sonucunu al ve veritabanı tarafından ID atanmış nesneyi kullan
        Result<User> savedUserResult = userService.saveUser(user);
        if (!savedUserResult.isSuccess()) {
            return Result.error("Kullanıcı kaydedilirken bir hata oluştu");
        }

        User savedUser = savedUserResult.getData();

        // Artık oluşturulan token'lar veritabanına başarıyla eklenmiş nesne üzerinden yürüyor
        String jwt = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(savedUser).getData().getToken();

        return Result.success(new AuthResponse(jwt, refreshToken));
    }

    @Override
    public Result<AuthResponse> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            return Result.error("Hatalı email veya şifre");
        }

        Result<User> userResult = userService.getUserByEmail(request.getEmail());
        if (!userResult.isSuccess()) return Result.error("Kullanıcı bulunamadı");

        User user = userResult.getData();
        String jwt = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken = refreshTokenService.createRefreshToken(user).getData().getToken();

        return Result.success(new AuthResponse(jwt, refreshToken));
    }

    @Override
    public Result<Void> logout(String token) {
        return refreshTokenService.revokeToken(token);
    }
}