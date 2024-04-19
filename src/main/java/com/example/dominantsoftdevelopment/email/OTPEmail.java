package com.example.dominantsoftdevelopment.email;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash(timeToLive = 86400)
public class OTPEmail {

    @Id
    private String email;

    private String name;

    private String password;

}
