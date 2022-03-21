package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@Data
@RequiredArgsConstructor
@Entity
@Table(name = "User_Details_Table")
public class User implements Serializable {

	@Id
	@SequenceGenerator(name = "user_id_sequence_generator", initialValue = 100, allocationSize = 1)
	@GeneratedValue(generator = "user_id_sequence_generator")
	private int userId;
	private String userCode;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String mailId;
	private String password;
	private String oldPassword;
	private String designation;
	private String department;
	private String address;
	private String linkedinId;
	private String status;
	private int profileCompleteness;
	private int isVerified;
	private Date createdDate;
	private Date modifiedDate;
	private String companyName;
	private int isDeleted;
	private String roles;
	private Long oneTimePassword;

}
