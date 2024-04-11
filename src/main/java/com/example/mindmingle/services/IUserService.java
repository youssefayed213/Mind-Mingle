package com.example.mindmingle.services;

import com.example.mindmingle.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User addUser(User user);

    User getUserById(int idU);

    List<User> getAllUsers();

    User updateUser(User user);

    void removeUser(int idU);

    List<User> getUsersByBirthDate(LocalDate date);

    List<User> getUsersByFirstNameAndLastName(String firstName, String lastName);

    //Optional<User> getUsersByEmail(String email);
}
