package com.rabo.bank.user.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rabo.bank.user.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {	

	Optional<User> findByUsernameAndPassword(String username, String password);
	
	Optional<User> findByUserId(int userId);

	Optional<User> findByUsername(String username);

}
