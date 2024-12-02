package com.ztech.UserService.Repository;

import com.ztech.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<com.ztech.UserService.Entity.User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
