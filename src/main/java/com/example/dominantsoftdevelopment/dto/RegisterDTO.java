package com.example.dominantsoftdevelopment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO (
        @NotBlank  String phoneNumber, @Email String email, @NotBlank String password, @NotBlank String perPassword,
        @NotBlank String firstName, @NotBlank String lastName, Long photo_id){
}

