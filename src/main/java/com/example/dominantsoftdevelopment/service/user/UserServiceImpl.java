package com.example.dominantsoftdevelopment.service.user;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.dto.UserProfilePatchDto;
import com.example.dominantsoftdevelopment.dto.UserResetPasswordDTO;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.OTPRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.service.SendSMS.SendSMSService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SendSMSService sendSMSService;
    private final OTPRepository otpRepository;

    @Override
    public ApiResult<UserProfileDto> profile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("user id = " + id + " not found "));
        return ApiResult.successResponse(modelMapper.map(user, UserProfileDto.class));
    }

    @Override
    public ApiResult<Boolean> updateProfile(Long id,  UserProfilePatchDto patchDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("user not found", HttpStatus.BAD_REQUEST));

        try {
            Class<?> entityClass = user.getClass();
            Class<? extends UserProfilePatchDto> patchDtoClass = patchDto.getClass();
            for (Field field : patchDtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(patchDto);
                if (value != null) {
                    try {
                        Field entityClassField = entityClass.getDeclaredField(field.getName());
                        entityClassField.setAccessible(true);
                        entityClassField.set(user, value);
                    } catch (NoSuchFieldException e) {
                        // If the field is not found in the current class, try to get it from the superclass
                        try {
                            Field entityClassField = entityClass.getField(field.getName());
                            entityClassField.setAccessible(true);
                            entityClassField.set(user, value);
                        } catch (NoSuchFieldException | IllegalAccessException ex) {
                            // Handle the exception as needed
                            ex.printStackTrace();
                        }
                    } catch (IllegalAccessException e) {
                        // Handle the exception as needed
                        e.printStackTrace();
                    }
                }
            }
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }

        /*try {
            Class<?> entityClass = user.getClass();
            Class<? extends UserProfilePatchDto> patchDtoClass = patchDto.getClass();
            for (Field field :patchDtoClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(patchDto);
                if (value != null) {
                    Field entityClassField = entityClass.getDeclaredField(field.getName());
                    entityClassField.setAccessible(true);
                    entityClassField.set(user, value);
                }
            }
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> resendSms(String phoneNumber) {
        // todo app to do this code
        return null;
    }

    @Override
    public ApiResult<Boolean> resetPassword(UserResetPasswordDTO resetPasswordDTO) {
        return null;
    }
}

