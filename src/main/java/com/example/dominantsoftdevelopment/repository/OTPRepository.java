package com.example.dominantsoftdevelopment.repository;

import com.example.dominantsoftdevelopment.otp.OTP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends CrudRepository<OTP, String> {
}
