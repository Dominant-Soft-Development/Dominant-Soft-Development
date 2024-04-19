package com.example.dominantsoftdevelopment.controller.user_controller;

import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.service.user_service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Userservice userservice;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> profile(@RequestParam("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userservice.profile(id));
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addUSer(@Payload  User user){
        userservice.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnect(@Payload User user){
        userservice.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectUsers(){
        return ResponseEntity.ok(userservice.findConnectUsers());
    }

}

