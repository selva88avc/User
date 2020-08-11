package com.rabo.bank.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import com.rabo.bank.user.model.JwtRequest;
import com.rabo.bank.user.model.JwtResponse;

@SpringBootTest(classes= {JpaTestConfig.class,UserApplication.class},webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@LocalServerPort
	private int port;

	private String getContextUrl() {
		return "http://localhost:" + port;
	}

	private static String token="";
	@Test
	void givenAuthRequest_shouldSucceedWith200() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("sujith");
		jwtRequest.setPassword("kumar");
		ResponseEntity<JwtResponse> result = template.
				postForEntity(getContextUrl()+"/authenticate", jwtRequest, JwtResponse.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		token=result.getBody().getJwttoken();
	}

	@Test
	void givenSignin_shouldSucceedWith200() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("vinith");
		jwtRequest.setPassword("kumar");
		ResponseEntity<JwtResponse> result = template.
				postForEntity(getContextUrl()+"/authenticate", jwtRequest, JwtResponse.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void givenSignin_shouldThrowUnauthorizedError() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("wrongusername");
		jwtRequest.setPassword("password");
		ResponseEntity<com.rabo.bank.user.model.JwtResponse> result = template.
				postForEntity(getContextUrl()+"/authenticate", jwtRequest, JwtResponse.class);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	void givenUserswithBearerToken_shouldSucceedWith200() throws UsernameNotFoundException {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("vinith");
		jwtRequest.setPassword("kumar");
		ResponseEntity<JwtResponse> response = template.
				postForEntity(getContextUrl()+"/authenticate", jwtRequest, JwtResponse.class);
		token=response.getBody().getJwttoken();	    	 
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "\n" + 
				"\n" + 
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aW5pdGgiLCJleHAiOjE1OTcwNTI1NzEsImlhdCI6MTU5NzAzNDU3MX0.a0jA8J1ivdNsfPc7GKe3Ug5DmBPMXXX87A99VLO5NCyuLgyDE2L3vlewb1EFWzC_qVM9tuugI2UXBoQ-i9Fnaw");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jwtRequest.toString() , headers);
		ResponseEntity<JwtResponse> result = template.postForEntity(getContextUrl()+"/users", entity, JwtResponse.class);
		assertNotNull(result);
	}
	@Test
	void givenUserswithWrongBearerToken_shouldThrowUnauthorizedError() throws UsernameNotFoundException {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("vinith");
		jwtRequest.setPassword("kumar");
		ResponseEntity<JwtResponse> response = template.
				postForEntity(getContextUrl()+"/authenticate", jwtRequest, JwtResponse.class);
		token=response.getBody().getJwttoken();	    	 
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token+1);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jwtRequest.toString() , headers);
		ResponseEntity<JwtResponse> result = template.postForEntity(getContextUrl()+"/users", entity, JwtResponse.class);
		assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

}
