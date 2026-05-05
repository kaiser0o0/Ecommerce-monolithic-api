package com.ecommerce.service;
import com.ecommerce.common.Result;
import com.ecommerce.entity.RefreshToken;
import com.ecommerce.entity.User;

public interface RefreshTokenService {
    Result<RefreshToken> createRefreshToken(User user);
    Result<RefreshToken> verifyExpiration(RefreshToken token);
    Result<Void> revokeToken(String token);
}