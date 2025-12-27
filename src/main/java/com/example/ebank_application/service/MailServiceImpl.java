package com.example.ebank_application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendCredentials(String toEmail, String login, String rawPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your eBank account credentials");
        message.setText(
                """
                Welcome to eBank 👋

                Your account has been created successfully.

                Login: %s
                Password: %s

                Please change your password after first login.

                Regards,
                eBank Team
                """.formatted(login, rawPassword)
        );

        mailSender.send(message);

        log.info("MAIL SENT to {}", toEmail);
    }
}
