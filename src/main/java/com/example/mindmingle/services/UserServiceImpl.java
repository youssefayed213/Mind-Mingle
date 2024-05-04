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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService,IUserService {

    UserRepository userRepository;
    MailService mailService;
    //private final PasswordEncoder passwordEncoder;

    public User addUser(User user,String password) {
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        String confirmationLink = "http://localhost:8085/minds/confirm-account?token=" + confirmationToken;
        mailService.sendConfirmationEmail(user.getEmail(), confirmationLink,user.getUsername(),password);

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

    @Transactional
    public void removeUser(int idU) {
        userRepository.deleteById(idU);
    }

    public List<User> getUserByPrenom(String firstName) {
        return userRepository.findByPrenomUser(firstName);
    }

    public List<User> getUserByNom(String lastname) {
        return userRepository.findByNomUser(lastname);
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


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public byte[] getImage(String username) throws IOException {

        byte[] img = userRepository.findByUsername(username).get().getImageProfil();
                decompressBytes(img);
        return img;
    }
    public User getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails instanceof User) {
            User user = (User) userDetails;

            // Check if the profile image is null
            if (user.getImageProfil() == null) {
                user.setImageProfil(new byte[0]);
            } else {
                // Decompress the image bytes if needed
                byte[] compressedImageBytes = user.getImageProfil();
                byte[] decompressedImageBytes = decompressBytes(compressedImageBytes);
                user.setImageProfil(decompressedImageBytes);
            }

            return user;
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    @Override
    public User updateUserProfile(User updatedUser) {
        // Retrieve the username of the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username:"+username);

        // Find the user entity in the database based on the username
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update user profile fields
        updateUserFields(existingUser, updatedUser);

        // Save and return the updated user profile
        return userRepository.save(existingUser);
    }

    public void uploadProfileImage(MultipartFile imageFile) throws IOException {

        // Retrieve the username of the authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username:"+username);

        // Find the user entity in the database based on the username
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Update profile image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Validate file type before processing
            if (!isValidImageType(imageFile)) {
                throw new IllegalArgumentException("Only JPG and PNG files are allowed for the image profile.");
            }

            // Process the image file (e.g., save it to storage)
            try {
                existingUser.setImageProfil(compressBytes(imageFile.getBytes()));
                userRepository.save(existingUser);
            } catch (IOException e) {
                throw new RuntimeException("Error processing image file", e);
            }
        }
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



    public Map<String, Object> getRegistrationStats(String timePeriod) {
        Map<String, Object> registrationStats = new HashMap<>();

        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (timePeriod) {
            case "weekly":
                startDate = endDate.minusWeeks(1);
                registrationStats.put("timePeriod", "weekly"); // Indicate the time period
                registrationStats.put("data", getDataForWeek(startDate, endDate)); // Retrieve data for the week
                break;
            case "monthly":
                startDate = endDate.with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1);
                registrationStats.put("timePeriod", "monthly"); // Indicate the time period
                registrationStats.put("data", getDataForMonth(startDate, endDate)); // Retrieve data for the month
                break;
            default:
                // Default to counting registrations for the last week
                startDate = endDate.minusWeeks(1);
                registrationStats.put("timePeriod", "weekly"); // Indicate the time period
                registrationStats.put("data", getDataForWeek(startDate, endDate)); // Retrieve data for the week
                break;
        }

        return registrationStats;
    }

    private Map<Integer, Integer> getDataForWeek(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Integer> data = new HashMap<>();
        // Loop through each day of the week and retrieve the number of registrations for that day
        for (int i = 1; i <= 7; i++) {
            LocalDate currentDate = startDate.plusDays(i - 1);

            // Calculate the end date by moving to the next day
            LocalDate nextDay = currentDate.plusDays(1);

            // Count registrations for the current day only
            int registrations = userRepository.countByRegistrationDateBetween(currentDate, nextDay.minusDays(1)); // Subtract one day from the next day
            data.put(i, registrations);
        }
        return data;
    }


    private Map<Integer, Integer> getDataForMonth(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Integer> data = new HashMap<>();
        int daysInMonth = startDate.getMonth().length(false);

        // Loop through each day of the month and retrieve the number of registrations for that day
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate currentDate = startDate.withDayOfMonth(i);

            // Calculate the end date by moving to the next day
            LocalDate nextDay = currentDate.plusDays(1);

            // Count registrations for the current day only
            int registrations = userRepository.countByRegistrationDateBetween(currentDate, nextDay.minusDays(1)); // Subtract one day from the next day
            data.put(i, registrations);
        }
        return data;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
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
