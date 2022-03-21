package com.example.demo.service;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.NewUserRegistrationDto;
import com.example.demo.dto.OtpVerificationRequestDto;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidDetailsException;
import com.example.demo.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class UserServiceImpl implements UserService {

	private static final String ACCOUNT_SID = "AC13796e1a4d757cd3a5079b590165a715";

	private static final String AUTH_TOKEN = "60506a914e73f9c41406d9d51e1c734a";

	private static final String FROM_NUMBER = "+19036648367";

	private Long phoneOtp;
	private Long mailOtp;

	@Autowired
	private JavaMailService javaMailService;

	@Autowired
	private UserRepository userRepository;

	public void sendOtpToPhone(String phoneNumber) {

		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			this.phoneOtp = ThreadLocalRandom.current().nextLong(100000, 1000000);

			String msg = "Your OTP - " + phoneOtp
					+ " please verify this OTP in your Application Which is sent by NachPay...";

			if (phoneNumber != null) {
				Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(FROM_NUMBER), msg).create();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public String sendOtp() {
		Long otp = ThreadLocalRandom.current().nextLong(100000l, 1000000l);
		String message = "Thanks for Registring with NachPay \nVerify Your Account and Your OTP Is : " + otp;
		try {
			javaMailService.sendOtpToMail("dbydby56@gmail.com", "dby123", message);
		} catch (MessagingException e) {
			throw new InvalidDetailsException("Invalid Details..");
		}
		return "OTP sent to the " + "dbydby56@gmail.com mail id. " + "please check..";
	}

	@Override
	public User userLogin(String email, String password) {
		User user = userRepository.findByMailId(email);
		user.setOldPassword(password);
		return user;
	}

	@Override
	public NewUserRegistrationDto newUserRegistration(NewUserRegistrationDto newUserRegistrationDto) {
		this.mailOtp = ThreadLocalRandom.current().nextLong(100000l, 1000000l);
		String mailMessage = "Thanks for Registring with NachPay\nVerify Your Account and Your email OTP Is : "
				+ this.mailOtp;

		try {
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			this.phoneOtp = ThreadLocalRandom.current().nextLong(100000, 1000000);

			String msg = "Your OTP - " + this.phoneOtp
					+ " please verify this OTP in your Application Which is sent by NachPay...";

			if (newUserRegistrationDto.getPhoneNumber() != null) {
				Message.creator(new PhoneNumber(newUserRegistrationDto.getPhoneNumber()), new PhoneNumber(FROM_NUMBER),
						msg).create();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		try {
			javaMailService.sendOtpToMail(newUserRegistrationDto.getMailId(), "New Registration", mailMessage);
			sendOtpToPhone(newUserRegistrationDto.getPhoneNumber());
			User user = new User();
			user.setFirstName(newUserRegistrationDto.getFirstName());
			user.setCompanyName(newUserRegistrationDto.getCompanyName());
			user.setPhoneNumber(newUserRegistrationDto.getPhoneNumber());
			user.setMailId(newUserRegistrationDto.getMailId());
			user.setOneTimePassword(mailOtp);
			userRepository.save(user);
			System.out.println("User Registered Successfully...");
			return newUserRegistrationDto;
		} catch (MessagingException e) {
			throw new InvalidDetailsException("Invalid Details..");
		}
	}

	@Override
	public String verifyOtp(OtpVerificationRequestDto otpVerificationRequestDto) {

		if (Objects.equals(this.mailOtp, otpVerificationRequestDto.getMailOtp())
				&& Objects.equals(this.phoneOtp, otpVerificationRequestDto.getPhoneOtp())) {
			return "Your OTP's are verified Successfully..Lets start your journey into NachPay Application..";
		} else {
			return "Something Went Wrong ..Please Check!!";
		}

	}
}