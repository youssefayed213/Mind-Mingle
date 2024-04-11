package com.example.mindmingle.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
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
}
