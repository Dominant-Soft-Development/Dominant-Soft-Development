package com.example.dominantsoftdevelopment.controller.user_controller;

import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.service.user_service.Userservice;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Userservice userservice;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> profile(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userservice.profile(id));
    }

    private final UserRepository userRepository;
//    @PostConstruct
//    public void user( ){
//        User user = new User();
//        user.setFirstName("sayyod");
//        user.setPassword("sayyjshdefviwe");
//        user.setLastName("sayyod");
//        user.setEmail("sayyod@gmail.com");
//        user.setPhoneNumber("927402934792347");
//        user.setAccountNonLocked(true);
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
//        user.setDeleted(false);
//
//        userRepository.save(user);
//    }

}
