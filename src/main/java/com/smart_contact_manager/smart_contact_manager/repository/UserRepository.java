package com.smart_contact_manager.smart_contact_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_contact_manager.smart_contact_manager.model.User;

public interface UserRepository extends JpaRepository<User, String> {
   Optional<User>findByEmail(String email);
   Optional<User>findByEmailAndPassword(String email,String password);
   Optional<User> findByEmailToken(String token);
}
