package com.example.dominantsoftdevelopment.controller.user_controller;

import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.service.user_service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Userservice userservice;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> profile(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userservice.profile(id));
    }
}
