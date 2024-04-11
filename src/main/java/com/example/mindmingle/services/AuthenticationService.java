package com.example.mindmingle.services;

import com.example.mindmingle.entities.AuthenticationResponse;
import com.example.mindmingle.entities.RoleUser;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        // Check if the role is admin, and if so, return an error or handle it appropriately
        if (request.getRole() == RoleUser.Admin) {
            // Handle the error or return a response indicating that admin registration is not allowed
            // For example:
            throw new IllegalArgumentException("Admin registration is not allowed through this method");
        }
        User user = new User();
        user.setNomUser(request.getNomUser());
        user.setPrenomUser(request.getPrenomUser());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateNaiss(request.getDateNaiss());
        user.setTel(request.getTel());
        user.setRole(request.getRole());
        // Determine the role and set the appropriate column value
        switch (request.getRole()) {
            case Etudiant:
                user.setNumEtudiant(request.getNumEtudiant());
                break;
            case Enseignant:
                user.setNumEnseignant(request.getNumEnseignant());
                break;
            case Expert:
                user.setNumExpert(request.getNumExpert());
                break;
            // Add additional cases for other roles if needed
        }

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new  AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtService.generateToken(user);

        return new  AuthenticationResponse(token);
    }
}
