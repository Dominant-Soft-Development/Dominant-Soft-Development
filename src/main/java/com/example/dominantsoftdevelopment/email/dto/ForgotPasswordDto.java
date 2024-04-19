package com.example.dominantsoftdevelopment.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgotPasswordDto {
    @Email
    @NotBlank(message = "auth.user.email.required")
    private String email;
    private String password;
    private String confirmPassword;
}
