package com.example.dominantsoftdevelopment.email;

import com.example.dominantsoftdevelopment.config.jwtFilter.JWTProvider;
import com.example.dominantsoftdevelopment.dto.OtpVerifyDto;
import com.example.dominantsoftdevelopment.email.dto.*;
import com.example.dominantsoftdevelopment.service.user_service.Userservice;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Userservice userService;
    private final EmailCodeService emailService;
    private final JWTProvider jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto ) {
        UserResponseDto userResponseDto = userService.signUp(userCreateDto);
        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( userResponseDto );
    }
    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto){
        UserResponseDto userResponseDto = userService.signIn(userSignInDto);
        String token = jwtService.createAccessToken(userResponseDto.getEmail());
        return ResponseEntity
                .status( HttpStatus.OK )
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .body( userResponseDto );
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verify(@RequestBody @Valid OtpVerifyEmailDto verifyDto){
        emailService.verifyCode(verifyDto);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( "successfully verified" );
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestParam @Email String email){
        emailService.sendEmail(email);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( "Successfully send. Please, check your email!" );
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        userService.forgotPassword(email);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body("Code sent");
    }
    @PostMapping("/forgot-password/verify-email")
    public ResponseEntity<?> forgotPasswordVerifyEmail(@RequestBody @Valid OtpVerifyEmailDto otpVerifyDto){
        UserResponseDto userResponseDto = emailService.forgotPasswordVerifyCode(otpVerifyDto);
        String token = jwtService.createAccessToken(userResponseDto.getEmail());
        return ResponseEntity
                .status( HttpStatus.OK )
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .body(userResponseDto);
    }

    @PostMapping("/forgot-password/new-password")
    public ResponseEntity<?> forgotPasswordNewPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto){
        UserResponseDto userResponseDto = userService.forgotPasswordNewPassword(forgotPasswordDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseDto);
    }

}
