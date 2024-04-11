package com.example.dominantsoftdevelopment;

import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableRedisRepositories
@RequiredArgsConstructor
public class DominantSoftDevelopmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DominantSoftDevelopmentApplication.class, args);
    }

}
