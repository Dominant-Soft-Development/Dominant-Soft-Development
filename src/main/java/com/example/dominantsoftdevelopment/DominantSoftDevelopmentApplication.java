package com.example.dominantsoftdevelopment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class DominantSoftDevelopmentApplication  {

    public static void main(String[] args) {
        SpringApplication.run(DominantSoftDevelopmentApplication.class, args);
    }
}
