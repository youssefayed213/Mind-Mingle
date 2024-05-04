package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.filter.JwtAuthenticationFilter;
import com.example.mindmingle.repositories.UserRepository;
import com.example.mindmingle.services.JWTService;
import com.example.mindmingle.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/home")
@CrossOrigin("*")
public class HomeController {

    private final UserServiceImpl userService;
    private final JWTService jwtService;
    UserRepository userRepository;

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
    @GetMapping("/findByUsername/{username}")
    public Optional<User> userByNom(@PathVariable String username){
        return userService.findByUsername(username);
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        try {
            User user = userService.getProfile();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody User updatedUser) {
        try {
            userService.updateUserProfile(updatedUser);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/uploadImageProfile")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            userService.uploadProfileImage(imageFile);
            return ResponseEntity.ok("Profile image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading profile image: " + e.getMessage());
        } catch (IllegalArgumentException e) {
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
