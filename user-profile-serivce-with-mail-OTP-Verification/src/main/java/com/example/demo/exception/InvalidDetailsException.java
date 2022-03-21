package com.example.demo.exception;

@SuppressWarnings("serial")
public class InvalidDetailsException extends RuntimeException {
	public InvalidDetailsException(String msg) {
		super(msg);
	}
}
