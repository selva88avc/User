package com.rabo.bank.user.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.user.config.JwtTokenUtil;
import com.rabo.bank.user.exception.AuthorizationException;
import com.rabo.bank.user.model.JwtRequest;
import com.rabo.bank.user.model.JwtResponse;
import com.rabo.bank.user.model.User;
import com.rabo.bank.user.service.UserDetailsServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS} )
public class JwtAuthenticationController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@ApiOperation(value = "Authenticate the login request and sign in", response = JwtResponse.class, tags = "authenticate")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!")})
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		logger.info("Entered createAuthenticationToken Method for user :{}", authenticationRequest.getUsername());
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		logger.info("Token generated for user :{}", authenticationRequest.getUsername());
		User user = userDetailsService.findByUsername(authenticationRequest.getUsername());		
		logger.info("Successfully fetched user details");
		JwtResponse response  = new JwtResponse();
		response.setJwttoken(token);
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setUserId(user.getUserId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setRole(user.getRole());
		return ResponseEntity.ok(response);
	}
	private void authenticate(String username, String password) throws AuthorizationException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			logger.info("Authentication Failed: {}", e.getMessage());
			throw new AuthorizationException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			logger.info("Authentication Failed: {}", e.getMessage());
			throw new AuthorizationException("INVALID_CREDENTIALS");

		}
	}
}