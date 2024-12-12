package org.codeplay.playcoolbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.codeplay.playcoolbackend.dto.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;


    public void sendEmail(EmailResponse emailResponse) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(emailResponse.getEmail());
            message.setSubject(emailResponse.getSubject());
            message.setText(emailResponse.getMessage());

            log.info("Sending email to: " + emailResponse.toString());
            mailSender.send(message);
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }
}