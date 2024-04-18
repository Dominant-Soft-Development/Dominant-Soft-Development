package com.example.dominantsoftdevelopment.service.user;

import com.example.dominantsoftdevelopment.dto.*;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.Orders;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.OTPRepository;
import com.example.dominantsoftdevelopment.repository.OrderRepository;
import com.example.dominantsoftdevelopment.repository.ProductRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.service.SendSMS.SendSMSService;
import com.example.dominantsoftdevelopment.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public ApiResult<Boolean> resendSms(String phoneNumber) {
        // todo
        return null;
    }

    @Override
    public ApiResult<Boolean> resetPassword(UserResetPasswordDTO resetPasswordDTO) {
        return null;
    }
}

