package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.services.IUserService;
import com.example.mindmingle.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/users")
@CrossOrigin("*")
public class AdminController {

   UserServiceImpl userService;
    PasswordEncoder passwordEncoder;

    @GetMapping("/admin-only")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello from admin only url");
    }


    @PostMapping("/adduser")
    public User addUser(@Valid @RequestBody User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user,password);
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        List<User> listUsers = userService.getAllUsers();
        return listUsers;
    }

    @PutMapping("/updateuser")
    public User updateUser(@Valid @RequestBody User user){
        User existingUser = userService.getUserById(user.getIdUser());

        if (user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteuser/{userId}")
    public void removeuser(@PathVariable ("userId") int iduser){
        userService.removeUser(iduser);
    }

    @GetMapping("/userByDate/{date}")
    public List<User> userByDate(@PathVariable LocalDate date){
        return userService.getUsersByBirthDate(date);
    }

    @GetMapping("/userByPrenom/{prenom}")
    public List<User> userByPrenom(@PathVariable String prenom){
        return userService.getUserByPrenom(prenom);
    }

    @GetMapping("/userByNom/{nom}")
    public List<User> userByNom(@PathVariable String nom){
        return userService.getUserByNom(nom);
    }

    @GetMapping("/userByPrenomEtNom/{prenom}/{nom}")
    public List<User> userByNames(@PathVariable String prenom, @PathVariable String nom){
        return userService.getUsersByFirstNameAndLastName(prenom,nom);
    }

    @GetMapping("/registration-stats")
    public ResponseEntity<Map<String, Object>> getRegistrationStats(@RequestParam String timePeriod) {
        Map<String, Object> registrationStats = userService.getRegistrationStats(timePeriod);
        return ResponseEntity.ok(registrationStats);
    }
}
