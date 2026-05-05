package com.ecommerce.service.impl;

import com.ecommerce.common.Result;
import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Result<User> getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .map(Result::success)
                .orElseGet(() -> Result.error("Kullanıcı bulunamadı"));
    }

    @Override
    public Result<User> saveUser(User user) {
        return Result.success(userRepository.save(user));
    }

    @Override
    public Result<Void> softDeleteUser(String email) {
        Result<User> userResult = getUserByEmail(email);
        if (!userResult.isSuccess()) return Result.error(userResult.getMessage());
        
        User user = userResult.getData();
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return Result.success(null, "Kullanıcı silindi");
    }
}