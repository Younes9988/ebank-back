package com.example.ebank_application.service;

public interface MailService {
    void sendCredentials(String toEmail, String login, String rawPassword);
}