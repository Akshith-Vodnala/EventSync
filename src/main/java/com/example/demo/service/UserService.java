package com.example.demo.service;

import com.example.demo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(String id);
    Optional<User> getUserByEmail(String email);
}
