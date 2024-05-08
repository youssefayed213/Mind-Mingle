package com.example.mindmingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    @Autowired
    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendResetPasswordEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("You have requested to reset your password. Please click on the following link to reset your password: " + resetLink);
        emailSender.send(message);
    }

    public  void sendConfirmationEmail(String to, String confirmationLink, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Confirmation Request");

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Welcome to MindMingle! Please confirm your account by clicking on the following link: ");
        emailBody.append(confirmationLink);
        emailBody.append("\nUsername: ").append(username);
        emailBody.append("\nPassword: ").append(password);

        // Set the concatenated message as the email body
        message.setText(emailBody.toString());

        emailSender.send(message);
    }

}
