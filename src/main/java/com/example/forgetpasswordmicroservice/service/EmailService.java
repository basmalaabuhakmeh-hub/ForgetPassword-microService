package com.example.forgetpasswordmicroservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private final RestTemplate restTemplate;

    @Value("${resend.api-key:}")
    private String apiKey;

    @Value("${resend.from}")
    private String from;

    @Value("${resend.url}")
    private String resendUrl;

    public EmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendOtp(String to, String otp) {
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Resend not configured. OTP for " + to + " = " + otp);
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("from", from);
        body.put("to", List.of(to));
        body.put("subject", "Password reset OTP");
        body.put("text", "Your OTP is " + otp + ". It expires in 10 minutes.");

        try {
            restTemplate.postForEntity(resendUrl, new HttpEntity<>(body, headers), String.class);
        } catch (Exception e) {
            System.out.println("Could not send email to " + to + " (" + e.getMessage() + "). OTP = " + otp);
        }
    }
}
