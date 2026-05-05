package com.ecommerce.service;
import com.ecommerce.common.Result;
import com.ecommerce.entity.User;

public interface UserService {
    Result<User> getUserByEmail(String email);
    Result<User> saveUser(User user);
    Result<Void> softDeleteUser(String email);
}