package com.example.dominantsoftdevelopment.controller.user;

import com.example.dominantsoftdevelopment.dto.*;
import com.example.dominantsoftdevelopment.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<UserProfileDto>> profile(@PathVariable Long id){
        return ResponseEntity.ok(userService.profile(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResult<Boolean>> updateProfile(@PathVariable Long id,
                                                            @RequestBody @Valid UserProfilePatchDto updateDto){
        return ResponseEntity.ok(userService.updateProfile(id, updateDto));
    }
    @PostMapping("/forget-password/{phoneNumber:.*}")
    @Operation(summary = "For users ,This API is used for forget password")
    public ResponseEntity<ApiResult<Boolean>> forgetPassword(@PathVariable String phoneNumber){
        return ResponseEntity.status(HttpStatus.OK).body(userService.resendSms(phoneNumber));
    }
    @PostMapping("/reset-password")
    @Operation(summary = "For users ,This API is used for forget password")
    public ResponseEntity<ApiResult<Boolean>> resetPassword(@RequestBody @Valid UserResetPasswordDTO resetPasswordDTO){
        return ResponseEntity.status(HttpStatus.OK) .body(userService.resetPassword(resetPasswordDTO));
    }
}
