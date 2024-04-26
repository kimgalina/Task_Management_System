package com.example.SpringBootWebApp.repository;

import com.example.SpringBootWebApp.entity.User;
import com.example.SpringBootWebApp.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    List<User> findByStatus(UserStatus status);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
