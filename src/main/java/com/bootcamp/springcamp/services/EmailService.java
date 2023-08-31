package com.bootcamp.springcamp.services;

public interface EmailService {
    void sendEmail(String userEmail, String subject, String body);
}
