package com.smart_contact_manager.smart_contact_manager.service;

import java.util.List;
import java.util.Optional;

import com.smart_contact_manager.smart_contact_manager.model.User;

public interface UserService {
    public User saveUser(User user);
    public Optional<User> getUserById(String id);
    public Optional<User> updateUser(User user);
    public boolean isUserExist(String id);
    public boolean isUserExistByEmail(String email);
    public void deleteUser(String id);
    public List<User> getAllUsers();
    public User getUserByEmail(String email);
    public User getUserByToken(String token);
}
