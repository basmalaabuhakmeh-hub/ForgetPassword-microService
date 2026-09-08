package com.example.forgetpasswordmicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpRequest {
    private String email;
}
