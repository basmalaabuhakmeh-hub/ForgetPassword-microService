package com.example.forgetpasswordmicroservice.controller;

import com.example.forgetpasswordmicroservice.dto.SendOtpRequest;
import com.example.forgetpasswordmicroservice.dto.VerifyOtpRequest;
import com.example.forgetpasswordmicroservice.service.ForgetPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final ForgetPasswordService forgetPasswordService;

    public OtpController(ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody SendOtpRequest request) {
        forgetPasswordService.sendOtp(request.getEmail());
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyOtpRequest request) {
        forgetPasswordService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("OTP verified");
    }
}
