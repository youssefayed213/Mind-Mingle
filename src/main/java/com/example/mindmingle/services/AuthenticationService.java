package com.example.mindmingle.services;

import com.example.mindmingle.entities.AuthenticationResponse;
import com.example.mindmingle.entities.RoleUser;
import com.example.mindmingle.entities.Token;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.TokenRepository;
import com.example.mindmingle.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final MailService mailService;

    private final TwilioSMSService smsService;

    private final TokenRepository tokenRepository;

    public AuthenticationService(UserRepository userRepository, UserServiceImpl userService, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, MailService mailService, TwilioSMSService smsService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
        this.smsService = smsService;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> register(User request) {
        // Check if the role is admin, and if so, return an error or handle it appropriately
        if (request.getRole() == RoleUser.Admin) {
            // Handle the error or return a response indicating that admin registration is not allowed
            throw new IllegalArgumentException("Admin registration is not allowed through this method");
        }

        // Create a new user
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

        // Generate confirmation token
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);

        // Save the user
        userRepository.save(user);

        // Generate JWT token
        //String token = jwtService.generateToken(user);

        // Send confirmation email
        String confirmationLink = "http://localhost:8085/minds/confirm-account?token=" + confirmationToken;
        mailService.sendConfirmationEmail(user.getEmail(), confirmationLink);

        // Return the authentication response with JWT token
        return ResponseEntity.ok("Confirm Account link sent to your email");
    }


    public AuthenticationResponse authenticate(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Authentication successful, reset failed login attempts counter
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
            user.resetFailedLoginAttempts();
            userRepository.save(user);

            // Check if the user account is confirmed
            if (user.getConfirmationToken() != null) {
                throw new IllegalStateException("User account is not confirmed");
            }

            // Generate JWT token
            String token = jwtService.generateToken(user);

            revokeAllTokensByUser(user);

            saveUserToken(token,user);

            return new AuthenticationResponse(token);
        } catch (AuthenticationException e) {
            // Authentication failed
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
            user.incrementFailedLoginAttempts(); // Increment failed login attempts
            if (user.getFailedLoginAttempts() >= 5) {
                user.setBlocked(true); // Block user if threshold is reached
                // Trigger SMS notification to inform the user of the block
                smsService.sendSMS(user.getTel(), "Your account has been blocked due to multiple failed login attempts.");
            }
            userRepository.save(user); // Save the updated user
            throw new BadCredentialsException("Invalid username or password");
        }
    }




    public ResponseEntity<String> forgotPassword(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User with provided email not found");
        }

        // Generate reset token
        String resetToken = jwtService.generateToken(user);

        // Send email with reset link
        String resetLink = "http://localhost:8085/minds/reset-password?token=" + resetToken;
        // Send email with reset link to user.getEmail()
        mailService.sendResetPasswordEmail(user.getEmail(), resetLink);

        return ResponseEntity.ok("Reset password link sent to your email");
    }

    public ResponseEntity<String> resetPassword(User request) {

        String newPassword = request.getPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("New password cannot be empty");
        }
        if (newPassword.length() < 8) {
            return ResponseEntity.badRequest().body("New password must be at least 8 characters long");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Extract the username from the token

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Update user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully");
    }

    public ResponseEntity<String> confirmAccount(String token) {
        // Find the user by the confirmation token
        User user = userService.findByConfirmationToken(token);
        if (user == null) {
            // User not found or token is invalid
            return ResponseEntity.badRequest().body("Invalid or expired confirmation token");
        }

        // Clear the confirmation token
        user.setConfirmationToken(null);
        // Save the updated user
        userRepository.save(user);

        return ResponseEntity.ok("Account confirmed successfully");
    }

    private void saveUserToken(String jwt, User user){
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedout(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
    private void revokeAllTokensByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokensByUser(user.getIdUser());

        if(!validTokenListByUser.isEmpty()){
            validTokenListByUser.forEach(t->{
                        t.setLoggedout(true);
                    }
            );
        }
        tokenRepository.saveAll(validTokenListByUser);
    }
}
