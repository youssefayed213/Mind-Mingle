package com.example.mindmingle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class Mailconfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");  // Serveur SMTP pour Office 365 (ou utilisez smtp-mail.outlook.com pour Outlook/Hotmail)
        mailSender.setPort(587);  // Port SMTP pour TLS
        mailSender.setUsername("rania.heni@esprit.tn");  // Adresse e-mail Microsoft
        mailSender.setPassword("fznjdoxdyhibhhsg");  // Mot de passe de l'e-mail Microsoft

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");  // Active le débogage pour voir les logs

        return mailSender;
    }


}



