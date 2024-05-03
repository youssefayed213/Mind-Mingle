package com.example.mindmingle.services;

import com.example.mindmingle.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {

    User addUser(User user, String password);

    User getUserById(int idU);

    List<User> getAllUsers();

    User updateUser(User user);

    void removeUser(int idU);

    List<User> getUsersByBirthDate(LocalDate date);

    List<User> getUsersByFirstNameAndLastName(String firstName, String lastName);

    User findByEmail(String email);

    User updateUserProfile(User updatedUser);

    User findByConfirmationToken(String token);

    void submitFeedbackForExpert(int expertId, Double feedbackScore);

    User getProfile();

   // Map<String, Integer> getRegistrationStats(String timePeriod);
    //void changePassword(String currentPassword, String newPassword);
}
