package com.taskmanager.application.controller;

import com.taskmanager.application.dto.AuthRequest;
import com.taskmanager.application.dto.AuthResponse;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.UserRepository;
import com.taskmanager.application.security.CustomUserDetailsService;
import com.taskmanager.application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (userRepo.findByUsername(request.username).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        userRepo.save(user);


        return ResponseEntity.ok("User registered");


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username);
        if (!passwordEncoder.matches(request.password, userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        System.out.println("UserDetails Password: " + userDetails.getPassword());
        System.out.println("Entered Raw Password: " + request.password);
        System.out.println("Match Result: " + passwordEncoder.matches(request.password, userDetails.getPassword()));

        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
