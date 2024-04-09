package com.example.dominantsoftdevelopment.service.auth;

import com.example.dominantsoftdevelopment.config.jwtFilter.JWTProvider;
import com.example.dominantsoftdevelopment.dto.ApiResult;
import com.example.dominantsoftdevelopment.dto.LoginDTO;
import com.example.dominantsoftdevelopment.dto.RegisterDTO;
import com.example.dominantsoftdevelopment.dto.TokenDTO;
import com.example.dominantsoftdevelopment.exceptions.RestException;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.AttachmentRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import com.example.dominantsoftdevelopment.service.emailService.EmailService;
import com.example.dominantsoftdevelopment.utils.AppConstants;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           @Lazy AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder, JWTProvider jwtProvider,
                           AttachmentRepository attachmentRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public ApiResult<TokenDTO> login(LoginDTO loginDTO) {
        User user1 = checkCredential(loginDTO.phoneNumber(), loginDTO.password());
        return ApiResult.successResponse(generateTokenDTO(user1));
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        if (!accessToken.startsWith(AppConstants.BEARER_TYPE))
            throw RestException.restThrow("Bearer emas");

        accessToken = accessToken.substring(AppConstants.BEARER_TYPE.length()).trim();
        refreshToken = refreshToken.substring(AppConstants.BEARER_TYPE.length()).trim();
        if (!jwtProvider.isExpired(accessToken))
            throw RestException.restThrow("Token muddati tugamagan");

        if (!jwtProvider.validRefreshToken(refreshToken))
            throw RestException.restThrow("Refresh token valid emas");

        String userId = jwtProvider.extractUserIdFromRefreshToken(refreshToken);
        User user = findUserById(Long.valueOf(userId))
                .orElseThrow(() -> RestException.restThrow("User not found: " + userId, HttpStatus.NOT_FOUND));

        TokenDTO tokenDTO = generateTokenDTO(user);

        return ApiResult.successResponse(tokenDTO);
    }

    @Override
    public ApiResult<?> register(RegisterDTO registerDTO) {

//        activatsya kodni tekshirish kerak phoneNumber buyicha

        if (!registerDTO.password().equals(registerDTO.perPassword()))
            throw RestException.restThrow("parrollar birxilmas", HttpStatus.BAD_REQUEST);

        return ApiResult.successResponse("successfully");
    }

    @Override
    public ApiResult<Boolean> sendEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        String generationCode = EmailService.getGenerationCode();

        if (byEmail.isPresent()) {
//            redisga saqlash kerak
            boolean b = EmailService.sendMessageToEmail(email, generationCode);

            if (!b) {
                throw RestException.restThrow("iltimos birozdan sung qayta urunib kuring", HttpStatus.BAD_REQUEST);
            }
            userRepository.save(byEmail.get());
            return ApiResult.successResponse(true);
        }

        boolean res = EmailService.sendMessageToEmail(email, generationCode);
        if (!res) {
            throw RestException.restThrow("iltimos birozdan sung qayta urunib kuring", HttpStatus.BAD_REQUEST);
        }

        return ApiResult.successResponse(true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public Optional<User> findUserById(Long userId) {
        if (userId == null)
            return Optional.empty();
        return userRepository.findById(userId);
    }

    private TokenDTO generateTokenDTO(User user) {
        String id = user.getId().toString();
        String accessToken = jwtProvider.createAccessToken(id);
        String refreshToken = jwtProvider.createRefreshAccessToken(id);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public User checkCredential(String username, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return (User) authentication.getPrincipal();
    }
}
