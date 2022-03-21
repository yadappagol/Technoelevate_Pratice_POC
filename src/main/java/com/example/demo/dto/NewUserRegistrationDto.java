package com.example.demo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUserRegistrationDto implements Serializable {
	private String firstName;
	private String companyName;
	private String phoneNumber;
	private String mailId;
}
