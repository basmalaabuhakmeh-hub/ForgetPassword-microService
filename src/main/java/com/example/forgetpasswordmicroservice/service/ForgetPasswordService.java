package com.example.forgetpasswordmicroservice.service;

import com.example.forgetpasswordmicroservice.Data_DBconnection.model.PasswordResetToken;
import com.example.forgetpasswordmicroservice.Data_DBconnection.repository.PasswordResetRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ForgetPasswordService {

    private final PasswordResetRepo passwordResetRepo;
    private final EmailService emailService;

    public ForgetPasswordService(PasswordResetRepo passwordResetRepo, EmailService emailService) {
        this.passwordResetRepo = passwordResetRepo;
        this.emailService = emailService;
    }

    @Transactional
    public void sendOtp(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        passwordResetRepo.deleteByEmail(email);

        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(otp);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        passwordResetRepo.save(resetToken);

        emailService.sendOtp(email, otp);
    }

    @Transactional
    public void verifyOtp(String email, String otp) {
        PasswordResetToken resetToken = passwordResetRepo.findByEmailAndToken(email, otp)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP"));
        if (resetToken.getExpiryDate() == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            passwordResetRepo.delete(resetToken);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP");
        }
        passwordResetRepo.delete(resetToken);
    }
}
