package com.ecommerce.mapper;

import com.ecommerce.dto.auth.RegisterRequest;
import com.ecommerce.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(RegisterRequest request, String encodedPassword) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());
        return user;
    }
}
