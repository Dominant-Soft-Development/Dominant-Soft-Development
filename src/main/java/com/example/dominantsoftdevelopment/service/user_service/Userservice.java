package com.example.dominantsoftdevelopment.service.user_service;

import com.example.dominantsoftdevelopment.dto.*;
import com.example.dominantsoftdevelopment.email.EmailCodeService;
import com.example.dominantsoftdevelopment.email.OTPEmail;
import com.example.dominantsoftdevelopment.email.dto.*;
import com.example.dominantsoftdevelopment.exceptions.EmailAlreadyExistException;
import com.example.dominantsoftdevelopment.exceptions.InvalidEmailAddressException;
import com.example.dominantsoftdevelopment.exceptions.PasswordNotMatchException;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.model.enums.Status;
import com.example.dominantsoftdevelopment.repository.OTPRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Userservice {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailCodeService emailService;
    private final OTPRepository otpRepository;

    @Transactional
    public UserProfileDto profile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> RestException.restThrow("user id = " + id + " not found "));

        UserProfileDto mapped = modelMapper.map(user, UserProfileDto.class);

        return mapped;
    }

    public void saveUser(User user)
    {

        user.setStatus(Status.ONLINE);
        userRepository.save(user);

    }

    public void disconnect(User user){

        var storedUser = userRepository.findById(user.getId()).orElse(null);
        if (storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User>  findConnectUsers(){

        return userRepository.findAllByStatus(Status.ONLINE);

    }


    public UserResponseDto signIn(UserSignInDto signInDto) {
        String password = signInDto.getPassword();
        User user = repository
                .findUserByEmail(signInDto.getEmail())
                .orElseThrow(
                        () -> new BadCredentialsException("Email or password incorrect")
                );

        if( !passwordEncoder.matches( password, user.getPassword() ) )
        {
            throw new BadCredentialsException( "Email or password doesn't match" );
        }
        return modelMapper.map(user , UserResponseDto.class);
    }

    @Transactional
    public UserResponseDto signUp(UserCreateDto userCreateDto) {

        String password = userCreateDto.getPassword();
        String confirmPassword = userCreateDto.getConfirmPassword();
        String email = userCreateDto.getEmail();

        if (!emailService.isValid(userCreateDto.getEmail())){
            throw new InvalidEmailAddressException("%s is not valid ".formatted(email));
        }
        if (repository.findUserByEmail(email).isPresent()){
            throw new EmailAlreadyExistException("User with email %s already exist".formatted(email));
        }
        if (!password.equals(confirmPassword)){
            throw new PasswordNotMatchException("Password and confirm password not matched!");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        emailService.sendEmail(email);

        OTPEmail otp = modelMapper.map(userCreateDto, OTPEmail.class);
        otpRepository.save(otp);

        return modelMapper.map(userCreateDto, UserResponseDto.class);
    }
}

