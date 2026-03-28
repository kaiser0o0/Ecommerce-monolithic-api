package com.ecommerce.service;

import com.ecommerce.entity.User;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // IoC - Constructor Injection
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı"));
    }

    public void softDeleteUser(String email) {
        User user = getUserByEmail(email);
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}