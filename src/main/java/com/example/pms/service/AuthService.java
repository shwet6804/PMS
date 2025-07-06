package com.example.pms.service;

import com.example.pms.entity.User;
import com.example.pms.repository.UserRepository;
import com.example.pms.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;  // ✅ For sending welcome email

    public String register(String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // ✅ Send welcome email after registration
        emailService.sendWelcomeEmail(email, email.split("@")[0]);

        return "User registered successfully";
    }

    public String login(String email, String password) {
        // ✅ Authenticate user with raw password (compared internally to encoded)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userRepository.findByEmail(email);
        return jwtService.generateToken(user);
    }

    public User getUserFromToken(String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
