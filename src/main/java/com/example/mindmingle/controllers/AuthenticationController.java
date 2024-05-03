package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.AuthenticationResponse;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.services.AuthenticationService;
import com.example.mindmingle.services.JWTService;
import com.example.mindmingle.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    UserServiceImpl userService;
    PasswordEncoder passwordEncoder;




    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User request){
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        return authenticationService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String newPassword) {
        return authenticationService.resetPassword(newPassword);
    }
    @GetMapping("/confirm-account")
    public ResponseEntity<String> confirmAccount(@RequestParam String token) {
        return authenticationService.confirmAccount(token);
    }




}
