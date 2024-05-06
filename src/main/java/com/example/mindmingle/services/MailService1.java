package com.example.mindmingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService1 {
    private static JavaMailSender emailSender;

    @Autowired
    public MailService1(JavaMailSender emailSender) {
        MailService1.emailSender = emailSender;
    }

    public static void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }
}

