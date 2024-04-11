package com.example.dominantsoftdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

    Long id;
    String firstName;
    String phoneNumber;
    String email;
}
