package com.authService.repositories;

import com.authService.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByEmail(String email);
}