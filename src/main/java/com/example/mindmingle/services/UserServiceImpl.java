package com.example.mindmingle.services;

import com.example.mindmingle.entities.RoleUser;
import com.example.mindmingle.entities.User;
import com.example.mindmingle.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService,IUserService {

    UserRepository userRepository;
    MailService mailService;
    //private final PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        String confirmationLink = "http://localhost:8085/minds/confirm-account?token=" + confirmationToken;
        mailService.sendConfirmationEmail(user.getEmail(), confirmationLink);

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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User updateUserProfile(User updatedUser, MultipartFile imageFile) {
        // Retrieve the username of the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the user entity in the database based on the username
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update user profile fields
        updateUserFields(existingUser, updatedUser);

        // Update profile image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Validate file type before processing
            if (!isValidImageType(imageFile)) {
                throw new IllegalArgumentException("Only JPG and PNG files are allowed for the image profile.");
            }

            // Process the image file (e.g., save it to storage)
            try {
                existingUser.setImageProfil(imageFile.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Error processing image file", e);
            }
        }

        // Save and return the updated user profile
        return userRepository.save(existingUser);
    }
    private void updateUserFields(User existingUser, User updatedUser) {
        if (updatedUser.getNomUser() != null && !updatedUser.getNomUser().isBlank()) {
            existingUser.setNomUser(updatedUser.getNomUser());
        }
        if (updatedUser.getPrenomUser() != null && !updatedUser.getPrenomUser().isBlank()) {
            existingUser.setPrenomUser(updatedUser.getPrenomUser());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank() && isValidEmail(updatedUser.getEmail())) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getDateNaiss() != null && (updatedUser.getDateNaiss().isBefore(LocalDate.now()))) {
            existingUser.setDateNaiss(updatedUser.getDateNaiss());
        }
        if (updatedUser.getTel() != null && !updatedUser.getTel().isBlank()) {
            existingUser.setTel(updatedUser.getTel());
        }
    }
    private boolean isValidImageType(MultipartFile file) {
        // Get the content type of the file
        String contentType = file.getContentType();
        // Check if the content type is JPG or PNG
        return contentType != null && (contentType.equals(MediaType.IMAGE_JPEG_VALUE) || contentType.equals(MediaType.IMAGE_PNG_VALUE));
    }
    private boolean isValidEmail(String email) {
        // Regular expression for a simple email format validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    @Override
    public User findByConfirmationToken(String token) {
        return userRepository.findByConfirmationToken(token).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void submitFeedbackForExpert(int expertId, Double feedbackScore) {

        // Get the authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String currentUsername = authentication.getName();
            Optional<User> optionalExpert = Optional.ofNullable(userRepository.findById(expertId).orElseThrow(() -> new IllegalArgumentException("Expert with id: "+ expertId+" not found " )));;
            if (optionalExpert.isPresent()) {
                User expert = optionalExpert.get();

                // Check if the user submitting feedback is the same as the expert
                if (expert.getUsername().equals(currentUsername)) {
                    throw new IllegalArgumentException("You cannot vote for yourself.");
                }

                // Check if the expert has the "Expert" role
                if (expert.getRole() != RoleUser.Expert) {
                    throw new IllegalArgumentException("Only users with the 'Expert' role can receive feedback.");
                }

                // Check if the feedback score is within a certain range
                if (feedbackScore < 0 || feedbackScore > 10) {
                    throw new IllegalArgumentException("Feedback score must be between 0 and 10.");
                }


                // Ensure totalFeedbackSubmissions is not null, initialize to 0 if it is
                int currentTotalFeedbackSubmissions = expert.getTotalFeedbackSubmissions() != null ? expert.getTotalFeedbackSubmissions().intValue() : 0;

                double currentTotalFeedbackScore = expert.getTotalFeedbackScore() != null ? expert.getTotalFeedbackScore() : 0.0;

                double newTotalFeedbackScore = currentTotalFeedbackScore + feedbackScore;
                int newTotalFeedbackSubmissions = currentTotalFeedbackSubmissions + 1;

                expert.setTotalFeedbackScore(newTotalFeedbackScore);
                expert.setTotalFeedbackSubmissions(newTotalFeedbackSubmissions);

                userRepository.save(expert);
            }
        }
    /*@Override
    public void changePassword(String currentPassword, String newPassword) {

        // Retrieve the username of the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Verify if the current password provided matches the stored password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password");
        }

        // Encode the new password before saving
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }*/
}
