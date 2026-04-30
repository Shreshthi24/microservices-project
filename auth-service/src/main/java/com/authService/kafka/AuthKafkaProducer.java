package com.authService.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuthKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAuthEvent(String message) {
        kafkaTemplate.send("auth-events", message);
    }
}