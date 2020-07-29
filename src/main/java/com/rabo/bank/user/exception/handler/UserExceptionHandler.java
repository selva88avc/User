package com.rabo.bank.user.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.user.exception.UserNotFoundException;
import com.rabo.bank.user.model.ApiError;
import com.rabo.bank.user.model.ReasonCode;

@ControllerAdvice
@RestController
public class UserExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
		ApiError apiError = new ApiError(ex.getMessage(), ReasonCode.USER_NOT_FOUND);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		return new ResponseEntity<Object>(apiError, headers, HttpStatus.NOT_FOUND);
	}
}
