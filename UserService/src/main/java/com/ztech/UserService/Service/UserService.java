package com.ztech.UserService.Service;

import com.ztech.UserService.DTO.LoginRequest;
import com.ztech.UserService.DTO.RegisterRequest;
import com.ztech.UserService.DTO.UserResponse;
import com.ztech.UserService.Entity.User;
import com.ztech.UserService.Exception.BadCredentialsException;
import com.ztech.UserService.Exception.DuplicateEmailException;
import com.ztech.UserService.Repository.UserRepository;
import com.ztech.UserService.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email or Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail(), user.getRole());

        // Return a UserResponse containing user details and JWT token
        return new UserResponse(user.getUsername(), user.getEmail(), user.getRole(), token);
    }

    public UserResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userOptional.get();
        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail(), user.getRole());

        // Return a UserResponse containing user details and JWT token
        return new UserResponse(user.getUsername(), user.getEmail(), user.getRole(), token);
    }
}

