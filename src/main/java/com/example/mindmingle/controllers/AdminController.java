package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.services.IUserService;
import com.example.mindmingle.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/users")
public class AdminController {

   UserServiceImpl userService;
    PasswordEncoder passwordEncoder;

    @GetMapping("/admin-only")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello from admin only url");
    }


    @PostMapping("/adduser")
    public User addUser(@Valid @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        List<User> listUsers = (List<User>)userService.getAllUsers();
        return listUsers;
    }

    @PutMapping("/updateuser")
    public User updateUser(@Valid @RequestBody User user){
        if (user.getPassword() != null) {
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

    @GetMapping("/userByPrenomEtNom/{prenom}/{nom}")
    public List<User> userByNames(@PathVariable String prenom, @PathVariable String nom){
        return userService.getUsersByFirstNameAndLastName(prenom,nom);
    }
}
