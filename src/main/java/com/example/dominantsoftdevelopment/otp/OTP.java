package com.example.dominantsoftdevelopment.otp;


import com.example.dominantsoftdevelopment.model.Attachment;
import com.example.dominantsoftdevelopment.model.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash(timeToLive = 3600 * 7)
public class OTP {
    @Id
    private String phoneNumber;

    private String firstname;

    private String lastname;

    private String email;

    private String lastName;

    private String password;
    private int code;

    private boolean accountNonLocked = true;

    private Attachment attachment;

    private Roles roles;

    private LocalDateTime sendTime;
    private int sentCount;
}
