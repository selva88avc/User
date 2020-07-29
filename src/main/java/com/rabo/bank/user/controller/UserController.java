package com.rabo.bank.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.bank.user.exception.UserNotFoundException;
import com.rabo.bank.user.model.User;
import com.rabo.bank.user.service.UserService;
@RestController
public class UserController {
	@Autowired
	UserService userService;
	@GetMapping(value="/", produces = MediaType.TEXT_PLAIN_VALUE)
	public String index() {

		return "This is Home page";
	}
	@GetMapping(path="users/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@RequestParam String username, @RequestParam String password) {
		System.out.println("======"+username);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		// This returns a JSON or XML with the users
		return new ResponseEntity<User>(userService.findByUserNameAndPassWord(username, password), headers, HttpStatus.ACCEPTED);
	}
	@GetMapping(path="users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<User>> getUsers() {
		System.out.println("inside getUsers");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		// This returns a JSON or XML with the users
		return new ResponseEntity<Iterable<User>>(userService.findAll(), headers, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path="users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserByUserId(@PathVariable int userId) {
		System.out.println("======"+userId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		// This returns a JSON or XML with the users
		return new ResponseEntity<User>(userService.findByUserId(userId), headers, HttpStatus.ACCEPTED);
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.OPTIONS} )
	@PostMapping(path="users", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> saveUser(@RequestBody User user) throws UserNotFoundException {
		System.out.println("user"+user);
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		headers.add("Access-Control-Allow-Headers", "*");
		headers.add("Access-Control-Expose-Headers", "Content-Type");
		User savedUser = userService.save(user);
		System.out.println("------------------------");
		// This returns a JSON or XML with the users
		return new ResponseEntity<User>(savedUser, headers, HttpStatus.ACCEPTED);
	}
}
