package com.example.ebank_application.presentation;

import com.example.ebank_application.dao.UserRepository;
import com.example.ebank_application.dto.*;
import com.example.ebank_application.security.JwtUtil;
import com.example.ebank_application.service.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {

        // 1️⃣ Authentification
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(), req.getPassword())
        );

        // 2️⃣ Charger l'utilisateur (pour le rôle)
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        // 3️⃣ Générer JWT avec ROLE
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(token);
    }
}
