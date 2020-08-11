package com.rabo.bank.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.user.exception.UserNotFoundException;
import com.rabo.bank.user.model.JwtResponse;
import com.rabo.bank.user.model.User;
import com.rabo.bank.user.service.UserDetailsServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS} )
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserDetailsServiceImpl userService;
	@ApiOperation(value = "Get all users", response = User[].class, tags = "authenticate")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 503, message = "DB down"),
			@ApiResponse(code = 401, message = "not authorized!")})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> getUsers() {
		logger.info("Entered get users method");
		return new ResponseEntity<>(userService.findAll(), HttpStatus.ACCEPTED);
	}
	@ApiOperation(value = "Authenticate the login request and sign in", response = JwtResponse.class, tags = "authenticate")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Success|OK"),
			@ApiResponse(code = 503, message = "DB down"),
			@ApiResponse(code = 401, message = "not authorized!")})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> saveUser(@RequestBody User user) throws UserNotFoundException {
		logger.info("Entered save user method for user: {}", user.getEmail());
		User savedUser = userService.save(user);
		return new ResponseEntity<>(savedUser, HttpStatus.ACCEPTED);
	}
}
