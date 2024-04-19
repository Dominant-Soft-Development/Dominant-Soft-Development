package com.example.dominantsoftdevelopment.controller.user;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.DiscountDTO;
import com.example.dominantsoftdevelopment.dto.UserDTO;
import com.example.dominantsoftdevelopment.dto.UserUpdateDTO;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
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

    private final UserService userservice;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> profile(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userservice.profile(id));
    }

    @GetMapping("/list")
    @Operation(summary = "This API is used for getting all users")
    public HttpEntity<ApiResult<Page<UserDTO>>> getDiscount(Pageable pageable, @RequestParam(required = false) String predicate) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userservice.getAll(pageable, predicate));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResult<Boolean>>edit(@PathVariable Long id,
                                         @Valid @RequestBody UserUpdateDTO updateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userservice.edit(id, updateDTO));
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


    @GetMapping("/onlines")
    public ResponseEntity<List<User>> findConnectUsers(){
        return ResponseEntity.ok(userservice.findConnectUsers());
    }

}

