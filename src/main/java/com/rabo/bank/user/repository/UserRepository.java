package com.rabo.bank.user.repository;


import org.springframework.data.repository.CrudRepository;

import com.rabo.bank.user.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {	

	User findByUserNameAndPassWord(String username, String password);
	
	User findByUserId(int userId);

}
