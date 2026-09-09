package com.example.forgetpasswordmicroservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailRequest {
    private String to;
    private String subject;
    private String text;
}
