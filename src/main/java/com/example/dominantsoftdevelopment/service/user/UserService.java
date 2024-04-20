package com.example.dominantsoftdevelopment.service.user;

import com.example.dominantsoftdevelopment.dto.*;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.Discount;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.model.enums.Status;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.rsql.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public UserDTO profile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> RestException.restThrow("user id = " + id + " not found "));
        return modelMapper.map(user, UserDTO.class);
    }

    public void saveUser(User user) {

        user.setStatus(Status.ONLINE);
        userRepository.save(user);

    }

    public void disconnect(User user) {

        var storedUser = userRepository.findById(user.getId()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectUsers() {

        return userRepository.findAllByStatus(Status.ONLINE);

    }

    public ApiResult<Boolean> edit(Long id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("user id = " + id + " not found "));
        try {
            Class<? extends UserUpdateDTO> updateDTOClass = updateDTO.getClass();
            Class<? extends User> entityClass = user.getClass();
            for (Field field : updateDTOClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(updateDTO);
                if (value != null) {
                    Field entityClassField = entityClass.getDeclaredField(field.getName());
                    entityClassField.setAccessible(true);
                    entityClassField.set(user, value);
                }
            }
            userRepository.save(user);
            return ApiResult.successResponse(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.successResponse(false);
    }

    public ApiResult<Page<UserDTO>> getAll(Pageable pageable, String predicate) {
        Specification<User> specification = SpecificationBuilder.build( predicate );
        if( specification == null ) {
            return ApiResult.successResponse(userRepository.findAll(pageable)
                    .map(user -> modelMapper.map(user, UserDTO.class)));
        }
        return ApiResult.successResponse(userRepository.findAll( specification, pageable )
                .map( user -> modelMapper.map(user, UserDTO.class)));
    }
}


