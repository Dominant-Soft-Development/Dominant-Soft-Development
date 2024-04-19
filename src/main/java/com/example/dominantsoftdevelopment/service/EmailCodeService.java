package com.example.dominantsoftdevelopment.service;

import com.example.dominantsoftdevelopment.dto.OtpVerifyEmailDto;
import com.example.dominantsoftdevelopment.dto.UserResponseDto;
import com.example.dominantsoftdevelopment.email.OTPEmail;
import com.example.dominantsoftdevelopment.model.EmailCode;
import com.example.dominantsoftdevelopment.exception.EmailVerificationException;
import com.example.dominantsoftdevelopment.model.User;
import com.example.dominantsoftdevelopment.repository.EmailCodeRepository;
import com.example.dominantsoftdevelopment.repository.OTPRepository;
import com.example.dominantsoftdevelopment.repository.UserRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class EmailCodeService {

    private final EmailCodeRepository emailCodeRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final OTPRepository otpRepository;
    private final ModelMapper mapper;
    private final String text = "Hi, someone tried to sign up for an DOMINANT SOFT DEVELOPMENT account with %s . " +
            "If it was you, enter this confirmation code in the app:" + System.lineSeparator()+
            " %d";
    @Transactional
    public void sendEmail(String email) {

        Optional<EmailCode> optionalEmailCode = emailCodeRepository.findById( email );
        System.out.println("optionalEmailCode = " + optionalEmailCode);
        if( optionalEmailCode.isEmpty() ) {

            int code = generateCode();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("DOMINANT SOFT DEVELOPMENT");
            message.setText(text.formatted(email,code));
            mailSender.send(message);

            EmailCode emailCode = new EmailCode( email, String.valueOf( code ), LocalDateTime.now(), 1 );
            emailCodeRepository.save(emailCode);
            System.out.println(emailCode);
            System.out.println( "Email code = "+code );
        }
        else
        {
            EmailCode emailCode = optionalEmailCode.get();

            if( emailCode.getSentCount() >= 5 )
            {
                throw new EmailVerificationException( "You already tried 5 times. Please try after 24 hour" );
            }

            if( !emailCode.getLastSentTime().plusMinutes(1).isBefore( LocalDateTime.now() ) )
            {
                Duration between = Duration.between( emailCode.getLastSentTime(), LocalDateTime.now() );
                long diff = 60 - between.getSeconds();
                throw new EmailVerificationException( "Please try after %d seconds".formatted( diff ) );
            }

            int code = generateCode();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("DOMINANT SOFT DEVELOPMENT ");
            message.setText(text.formatted(email,code));
            mailSender.send(message);


            emailCode.setCode( String.valueOf( code ) );
            emailCode.setSentCount( emailCode.getSentCount() + 1 );
            emailCode.setLastSentTime( LocalDateTime.now() );

            emailCodeRepository.save( emailCode );

            System.out.println( "Email code = "+code );
        }
    }

    public void verifyCode( OtpVerifyEmailDto verifyDto )
    {
        String email = verifyDto.getEmail();
        System.out.println("email = " + email);
        String code = String.valueOf(verifyDto.getCode());

        EmailCode emailCode = emailCodeRepository.findById( email )
                                    .orElseThrow( () -> new EmailVerificationException( "the email code already expired" ) );

        if( emailCode.getLastSentTime().plusMinutes( 5 ).isBefore( LocalDateTime.now() ) ) {
            throw new EmailVerificationException( "the email code already expired" );
        }

        if( !emailCode.getCode().equals( code ) ) {
            throw new EmailVerificationException( "Email code doesn't match" );
        }

        OTPEmail otpUser = otpRepository.findByEmail( email )
                                    .orElseThrow(
                                      () -> new EntityNotFoundException(
                                          String.format( "User with email = %s not found", email)
                                      ) );

        userRepository.save(mapper.map(otpUser, User.class));
        otpRepository.deleteById(email);
    }




    public boolean isValid(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
    private int generateCode(){
        Random random = new Random();
        return random.nextInt( 100000, 999999 );
    }

    public UserResponseDto forgotPasswordVerifyCode(OtpVerifyEmailDto verifyDto) {



        String email = verifyDto.getEmail();
        System.out.println("email = " + email);
        String code = String.valueOf(verifyDto.getCode());

        EmailCode emailCode = emailCodeRepository.findById( email )
                .orElseThrow( () -> new EmailVerificationException( "the email code already expired" ) );

        if( emailCode.getLastSentTime().plusMinutes( 5 ).isBefore( LocalDateTime.now() ) ) {
            throw new EmailVerificationException( "the email code already expired" );
        }

        if( !emailCode.getCode().equals( code ) ) {
            throw new EmailVerificationException( "Email code doesn't match" );
        }
        otpRepository.deleteById(email);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email not found"));
        return mapper.map(user,UserResponseDto.class);
    }
}
