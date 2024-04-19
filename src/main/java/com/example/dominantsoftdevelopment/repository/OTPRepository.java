package com.example.dominantsoftdevelopment.repository;

import com.example.dominantsoftdevelopment.email.OTPEmail;
import com.example.dominantsoftdevelopment.otp.OTP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends CrudRepository<OTP, String> {
    Optional<OTP> findByPhoneNumber(String phoneNumber);
    Optional<OTPEmail> findByEmail(String email);
}
