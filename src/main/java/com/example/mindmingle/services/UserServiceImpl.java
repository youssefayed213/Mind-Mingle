package com.example.mindmingle.services;

import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService,IUserService {

    UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }


    public User getUserById(int idU) {
        return userRepository.findById(idU).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public void removeUser(int idU) {
        userRepository.deleteById(idU);
    }


    public List<User> getUsersByBirthDate(LocalDate date) {
        return userRepository.findByDateNaiss(date);
    }


    public List<User> getUsersByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByPrenomUserAndNomUser(firstName, lastName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


   /* public Optional<User> getUsersByEmail(String email) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }*/


}
