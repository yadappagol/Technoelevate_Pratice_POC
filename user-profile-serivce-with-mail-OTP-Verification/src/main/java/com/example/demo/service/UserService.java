package com.example.demo.service;

import com.example.demo.dto.NewUserRegistrationDto;
import com.example.demo.dto.OtpVerificationRequestDto;
import com.example.demo.entity.User;

public interface UserService {

	String sendOtp();

	User userLogin(String email, String password);

	String verifyOtp(OtpVerificationRequestDto otpVerificationRequestDto);

	NewUserRegistrationDto newUserRegistration(NewUserRegistrationDto newUserRegistrationDto);

}
