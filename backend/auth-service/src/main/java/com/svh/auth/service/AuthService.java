package com.svh.auth.service;

import com.svh.auth.dto.AuthResponse;
import com.svh.auth.dto.LoginRequest;
import com.svh.auth.dto.RegisterRequest;
import com.svh.auth.entity.User;
import com.svh.auth.repository.UserRepository;
import com.svh.auth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .message("Email already registered")
                    .build();
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .role(User.UserRole.valueOf(request.getRole() != null ? request.getRole() : "STUDENT"))
                .isActive(true)
                .isEmailVerified(false)
                .build();

        user = userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .message("Registration successful")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return AuthResponse.builder()
                    .message("User not found")
                    .build();
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return AuthResponse.builder()
                    .message("Invalid password")
                    .build();
        }

        if (!user.getIsActive()) {
            return AuthResponse.builder()
                    .message("User account is inactive")
                    .build();
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());

        log.info("User logged in successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .message("Login successful")
                .build();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}

