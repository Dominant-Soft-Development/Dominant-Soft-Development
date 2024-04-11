package com.example.dominantsoftdevelopment.service.user_service;

import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Userservice {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Transactional
    public UserProfileDto profile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> RestException.restThrow("user id = " + id + " not found "));

        UserProfileDto mapped = modelMapper.map(user, UserProfileDto.class);

        return mapped;
    }
}

