package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NewUserRegistrationDto;
import com.example.demo.dto.OtpVerificationRequestDto;
import com.example.demo.entity.User;
import com.example.demo.response.ResponseMessage;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * URL : http://localhost:8050/api/v1/user/new-registration
	 */
	@PostMapping("/new-registration")
	public NewUserRegistrationDto newUserRegistration(@RequestBody NewUserRegistrationDto newUserRegistrationDto) {
		return userService.newUserRegistration(newUserRegistrationDto);
	}

	/**
	 * 
	 * URL : http://localhost:8050/api/v1/user/sendOtp
	 */
	@GetMapping("/sendOtp")
	public ResponseEntity<ResponseMessage> sendOTP() {
		String sendOtp = userService.sendOtp();
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false, "OTP details..",
				sendOtp);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 
	 * URL : http://localhost:8050/api/v1/user/verify-otp
	 */
	@PostMapping(value = "/verify-otp")
	public String otpVerificationHandler(@RequestBody OtpVerificationRequestDto otpVerificationRequestDto) {
		return userService.verifyOtp(otpVerificationRequestDto);
	}

	/**
	 * 
	 * URL : http://localhost:8050/api/v1/user/user-login
	 */
	@PostMapping("/user-login")
	public ResponseEntity<ResponseMessage> userLogin(@RequestBody String email, String password) {

		User user = userService.userLogin(email, password);
		ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), new Date(), false,
				"User details..", user);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

}
