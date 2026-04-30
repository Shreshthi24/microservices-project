package com.emailService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "auth-events", groupId = "email-group")
    public void consume(String message) {

        System.out.println("Received: " + message);

        String[] data = message.split("\\|");

        String email = data[0];
        String status = data[1];

        if ("SUCCESS".equalsIgnoreCase(status)) {

            sendEmail(
                    email,
                    "Login Successful",
                    "Welcome " + email + ", you have successfully logged in."
            );

        } else {

            sendEmail(
                    email,
                    "Login Failed Alert",
                    "Login attempt failed for your account: " + email
            );
        }
    }

    private void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        System.out.println("EMAIL SENT → " + to);
    }
}