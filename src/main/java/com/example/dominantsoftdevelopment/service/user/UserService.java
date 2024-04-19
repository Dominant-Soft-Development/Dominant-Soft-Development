package com.example.dominantsoftdevelopment.service.user;

import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.UserProfileDto;
import com.example.dominantsoftdevelopment.dto.UserProfilePatchDto;
import com.example.dominantsoftdevelopment.dto.UserResetPasswordDTO;

public interface UserService {
    ApiResult<UserProfileDto> profile(Long id);

    ApiResult<Boolean> resendSms(String phoneNumber);

    ApiResult<Boolean> resetPassword(UserResetPasswordDTO resetPasswordDTO);

    ApiResult<Boolean> updateProfile(Long id, UserProfilePatchDto patchDto);

}