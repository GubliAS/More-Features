package com.example.auth.controller;

import com.example.auth.security.JwtUtil;
import com.example.auth.repository.SiteUserRepository;
import com.example.commonentities.SiteUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private SiteUserRepository siteUserRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return siteUserRepository.findByEmailAddress(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .<ResponseEntity<?>>map(user -> {
                    String token = jwtUtil.generateToken(user.getEmailAddress());
                    return ResponseEntity.ok(new AuthResponse(token));
                })
                .orElse(ResponseEntity.status(401).body(new ErrorResponse("Invalid credentials")));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody TokenRequest request) {
        try {
            String username = jwtUtil.extractUsername(request.getToken());
            boolean valid = jwtUtil.validateToken(request.getToken(), username);
            if (valid) {
                return ResponseEntity.ok("Token is valid for user: " + username);
            } else {
                return ResponseEntity.status(401).body(new ErrorResponse("Invalid or expired token"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ErrorResponse("Invalid or expired token"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (siteUserRepository.findByEmailAddress(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email already registered"));
        }
        
        SiteUser user = new SiteUser();
        user.setEmailAddress(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        siteUserRepository.save(user);
        
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        return siteUserRepository.findByEmailAddress(request.getEmail())
                .<ResponseEntity<?>>map(user -> {
                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    siteUserRepository.save(user);
                    return ResponseEntity.ok("Password reset successfully");
                })
                .orElse(ResponseEntity.badRequest().body(new ErrorResponse("User not found")));
    }

    @Data
    public static class AuthRequest {
        private String email;
        private String password;
    }

    @Data
    public static class AuthResponse {
        private final String token;
    }

    @Data
    public static class TokenRequest {
        private String token;
    }

    @Data
    public static class PasswordResetRequest {
        private String email;
        private String newPassword;
    }

    @Data
    public static class ErrorResponse {
        private final String error;
    }
} 