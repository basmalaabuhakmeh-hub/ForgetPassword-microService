package com.example.forgetpasswordmicroservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
//send the OTP to the user's email
@Service
public class EmailService {

    private final JavaMailSender mailSender;//This is the object that communicates with the mail server

    @Value("${spring.mail.username:}")//application.properties contain: spring.mail.username=myemail@gmail.com
    private String from;//represents the email address that the email is being sent from

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtp(String to, String otp) {
        if (from == null || from.isBlank()) {
            System.out.println("Mail not configured. OTP for " + to + " = " + otp);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Password reset OTP");
            message.setText("Your OTP is " + otp + ". It expires in 10 minutes.");
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Could not send email to " + to + ". OTP = " + otp);
        }
    }
}
