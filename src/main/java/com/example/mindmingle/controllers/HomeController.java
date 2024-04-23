package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/home")
public class HomeController {

    private final UserServiceImpl userService;
    @GetMapping("/welcome")
    public ResponseEntity<String> home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = getMessageForUserRole(auth);

        return ResponseEntity.ok(message);
    }

    private String getMessageForUserRole(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();
        switch (role) {
            case "Enseignant":
                return "Hello from enseignant";
            case "Expert":
                return "Hello from expert";
            case "Etudiant":
                return "Hello from etudiant";
            case "Admin":
                return "Hello from admin only ";
            default:
                return "Hello from unknown user";
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDetails user = userService.loadUserByUsername(username);
        return ResponseEntity.ok((User) user);
    }
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestPart(value = "imageProfil", required = false) MultipartFile imageFile,
                                                @RequestPart("updatedUser") User updatedUser,
                                                BindingResult bindingResult) {
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                // Handle validation errors
                return ResponseEntity.badRequest().body("Validation errors occurred: " + bindingResult.getAllErrors());
            }

            // Update the user profile
            userService.updateUserProfile(updatedUser, imageFile);

            return ResponseEntity.ok("Profile updated successfully");
        } catch (IllegalArgumentException e) {
            // Handle exceptions thrown by the service
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/submitFeedBack")
    public ResponseEntity<String> submitFeedback(@RequestParam int expertId, @RequestParam double feedbackScore) {
        try {
            userService.submitFeedbackForExpert(expertId, feedbackScore);
            return ResponseEntity.ok("Feedback submitted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

  /*  @PutMapping("/profile/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String currentPassword,@RequestParam String newPassword) {
        userService.changePassword(currentPassword,newPassword);
        return ResponseEntity.ok("Password changed successfully");
    }*/

}
