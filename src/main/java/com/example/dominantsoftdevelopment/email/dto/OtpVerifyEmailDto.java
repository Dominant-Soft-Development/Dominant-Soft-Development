package com.example.dominantsoftdevelopment.email.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtpVerifyEmailDto {

    @NotBlank
    private String email;

    private int code;
}
