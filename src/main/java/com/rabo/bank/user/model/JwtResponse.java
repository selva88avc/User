package com.rabo.bank.user.model;
import java.io.Serializable;
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	private  String jwttoken;
	private  int userId;
	private  String email;
	private  String firstName;
	private  String lastName;
	private  String username;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JwtResponse(String jwttoken, int userId, String email, String firstName, String lastName, String username) {
		super();
		this.jwttoken = jwttoken;
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	public JwtResponse() {
		// TODO Auto-generated constructor stub
	}
}