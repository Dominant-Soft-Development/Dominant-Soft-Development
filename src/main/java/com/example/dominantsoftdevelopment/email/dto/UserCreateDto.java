package com.example.dominantsoftdevelopment.email.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreateDto extends UserBaseDto {

    private String password;

    private String confirmPassword;

}
