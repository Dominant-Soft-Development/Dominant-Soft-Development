package com.example.dominantsoftdevelopment;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@RequiredArgsConstructor
public class DominantSoftDevelopmentApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DominantSoftDevelopmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
