package com.example.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Project Management System");
        message.setText("Hello " + name + ",\n\n"
                + "Welcome to the Project Management System!\n"
                + "Your account has been successfully created.\n\n"
                + "Happy managing projects! 🚀\n\n"
                + "Regards,\nTeam PMS");

        mailSender.send(message);
    }
}
