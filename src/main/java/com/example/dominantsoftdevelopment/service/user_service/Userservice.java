package com.example.dominantsoftdevelopment.service.user_service;

import com.example.dominantsoftdevelopment.dto.*;
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
}

