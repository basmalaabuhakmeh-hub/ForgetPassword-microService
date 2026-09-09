package com.example.forgetpasswordmicroservice.controller;

import com.example.forgetpasswordmicroservice.dto.SendEmailRequest;
import com.example.forgetpasswordmicroservice.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody SendEmailRequest request) {
        if (request.getTo() == null || request.getTo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "to is required");
        }
        if (request.getSubject() == null || request.getSubject().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "subject is required");
        }
        if (request.getText() == null || request.getText().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "text is required");
        }
        emailService.sendEmail(request.getTo(), request.getSubject(), request.getText());
        return ResponseEntity.ok("Email sent");
    }
}
