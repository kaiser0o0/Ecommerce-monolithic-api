package com.ecommerce.service.impl;

import com.ecommerce.common.Result;
import com.ecommerce.entity.RefreshToken;
import com.ecommerce.entity.User;
import com.ecommerce.repository.RefreshTokenRepository;
import com.ecommerce.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Result<RefreshToken> createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(86400000));
        return Result.success(refreshTokenRepository.save(refreshToken));
    }

    @Override
    public Result<RefreshToken> verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return Result.error("Refresh token süresi dolmuş. Lütfen tekrar giriş yapın.");
        }
        return Result.success(token);
    }

    @Override
    public Result<Void> revokeToken(String token) {
        Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByToken(token);
        if (tokenOpt.isPresent()) {
            RefreshToken refreshToken = tokenOpt.get();
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
            return Result.success(null, "Token başarıyla iptal edildi");
        }
        return Result.error("Token bulunamadı");
    }
}