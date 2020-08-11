package com.rabo.bank.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.user.exception.AuthorizationException;
import com.rabo.bank.user.exception.UserNotFoundException;
import com.rabo.bank.user.model.ApiError;
import com.rabo.bank.user.model.ReasonCode;

@ControllerAdvice
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS} )
public class UserExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
		ApiError apiError = new ApiError(ex.getMessage(), ReasonCode.USER_NOT_FOUND);
		return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(AuthorizationException.class)
	protected ResponseEntity<Object> handleAuthorizationException(AuthorizationException ex){
		ApiError apiError = new ApiError(ex.getMessage(), ReasonCode.USER_NOT_AUTHORIZED);
		return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
	}
}
