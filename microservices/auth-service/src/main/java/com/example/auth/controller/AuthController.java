package com.example.auth.controller;

import com.example.auth.security.JwtUtil;
import com.example.auth.repository.SiteUserRepository;
import com.example.auth.repository.RoleRepository;
import com.example.auth.service.EmailService;
import com.example.commonentities.SiteUser;
import com.example.commonentities.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Autowired
    private SiteUserRepository siteUserRepository;
    @Autowired
    private RoleRepository roleRepository;

    // In-memory storage for verification codes (in production, use Redis or database)
    private final Map<String, String> verificationCodes = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return siteUserRepository.findByEmailAddress(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .<ResponseEntity<?>>map(user -> {
                    String role = user.getRoles().iterator().next().getName();
                    String token = jwtUtil.generateTokenWithRole(user.getEmailAddress(), role);
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
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (siteUserRepository.findByEmailAddress(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email already registered"));
        }
        
        SiteUser user = new SiteUser();
        user.setEmailAddress(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        
        // Assign role based on registration type
        Set<Role> roles = new HashSet<>();
        if ("SELLER".equals(request.getRole())) {
            // Add seller-specific fields
            user.setStoreName(request.getStoreName());
            user.setIdCardType(request.getIdCardType());
            user.setIdCardCountry(request.getIdCardCountry());
            user.setIdCardNumber(request.getIdCardNumber());
            user.setIsVerified(false);
            user.setVerificationStatus("PENDING");
            
            // Assign SELLER role
            Role sellerRole = roleRepository.findByName("SELLER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("SELLER");
                    return roleRepository.save(newRole);
                });
            roles.add(sellerRole);
            
            System.out.println("=== SELLER REGISTRATION WITH ID VERIFICATION ===");
            System.out.println("Store Name: " + request.getStoreName());
            System.out.println("ID Card Type: " + request.getIdCardType());
            System.out.println("ID Card Country: " + request.getIdCardCountry());
            System.out.println("ID Card Number: " + request.getIdCardNumber());
            System.out.println("Role Assigned: SELLER");
            System.out.println("=== VALIDATION STATUS ===");
            System.out.println("✅ Frontend validation passed (format check)");
            System.out.println("⚠️  Backend validation: Basic format only");
            System.out.println("ℹ️  Note: Real verification requires government API integration");
            System.out.println("=== NEXT STEPS ===");
            System.out.println("1. Admin review of ID verification data");
            System.out.println("2. Manual verification or third-party service integration");
            System.out.println("3. Account approval/rejection process");
            System.out.println("================================================");
        } else {
            // Assign BUYER role (default)
            Role buyerRole = roleRepository.findByName("BUYER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("BUYER");
                    return roleRepository.save(newRole);
                });
            roles.add(buyerRole);
            
            System.out.println("=== BUYER REGISTRATION ===");
            System.out.println("Role Assigned: BUYER");
            System.out.println("================================================");
        }
        
        user.setRoles(roles);
        siteUserRepository.save(user);
        
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody VerificationCodeRequest request) {
        // Check if user exists
        Optional<SiteUser> user = siteUserRepository.findByEmailAddress(request.getEmail());
        if (user.isPresent()) {
            // Generate a simple 4-digit code
            String code = String.format("%04d", (int)(Math.random() * 10000));
            verificationCodes.put(request.getEmail(), code);
            
            // Send verification code via email
            emailService.sendVerificationCode(request.getEmail(), code);
            
            return ResponseEntity.ok(new MessageResponse("Verification code sent"));
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("User not found"));
        }
    }

    @PostMapping("/send-registration-code")
    public ResponseEntity<?> sendRegistrationCode(@RequestBody VerificationCodeRequest request) {
        // Check if user already exists
        if (siteUserRepository.findByEmailAddress(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email already registered"));
        }
        
        // Generate a simple 4-digit code
        String code = String.format("%04d", (int)(Math.random() * 10000));
        verificationCodes.put(request.getEmail(), code);
        
        // Send registration code via email
        emailService.sendRegistrationCode(request.getEmail(), code);
        
        return ResponseEntity.ok(new MessageResponse("Registration code sent"));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest request) {
        String storedCode = verificationCodes.get(request.getEmail());
        if (storedCode != null && storedCode.equals(request.getCode())) {
            verificationCodes.remove(request.getEmail()); // Remove used code
            return ResponseEntity.ok(new MessageResponse("Code verified successfully"));
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid verification code"));
        }
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

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(new ErrorResponse("Missing or invalid Authorization header"));
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Optional<SiteUser> userOpt = siteUserRepository.findByEmailAddress(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("User not found"));
        }
        SiteUser user = userOpt.get();
        String role = user.getRoles().iterator().next().getName();
        Map<String, Object> profile = new HashMap<>();
        profile.put("email", user.getEmailAddress());
        profile.put("firstName", user.getFirstName());
        profile.put("lastName", user.getLastName());
        profile.put("phoneNumber", user.getPhoneNumber());
        profile.put("role", role);
        profile.put("storeName", user.getStoreName());
        profile.put("idCardType", user.getIdCardType());
        profile.put("idCardCountry", user.getIdCardCountry());
        profile.put("idCardNumber", user.getIdCardNumber());
        profile.put("verificationStatus", user.getVerificationStatus());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateProfileRequest req) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(new ErrorResponse("Missing or invalid Authorization header"));
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Optional<SiteUser> userOpt = siteUserRepository.findByEmailAddress(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("User not found"));
        }
        SiteUser user = userOpt.get();
        if (req.getFirstName() != null) user.setFirstName(req.getFirstName());
        if (req.getLastName() != null) user.setLastName(req.getLastName());
        if (req.getPhoneNumber() != null) user.setPhoneNumber(req.getPhoneNumber());
        siteUserRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody ChangePasswordRequest req) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(new ErrorResponse("Missing or invalid Authorization header"));
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Optional<SiteUser> userOpt = siteUserRepository.findByEmailAddress(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("User not found"));
        }
        SiteUser user = userOpt.get();
        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(400).body(new ErrorResponse("Current password is incorrect"));
        }
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        siteUserRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam String email) {
        Optional<SiteUser> userOpt = siteUserRepository.findByEmailAddress(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("User not found"));
        }
        siteUserRepository.delete(userOpt.get());
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    @Data
    public static class AuthRequest {
        private String email;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String storeName;
        private String idCardType;
        private String idCardCountry;
        private String idCardNumber;
        private String role;
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
    public static class VerificationCodeRequest {
        private String email;
    }

    @Data
    public static class VerifyCodeRequest {
        private String email;
        private String code;
    }

    @Data
    public static class ErrorResponse {
        private final String error;
    }

    @Data
    public static class MessageResponse {
        private final String message;
    }

    @Data
    public static class UpdateProfileRequest {
        private String firstName;
        private String lastName;
        private String phoneNumber;
    }
    @Data
    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
    }
} 