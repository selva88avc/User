package com.rabo.bank.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabo.bank.user.exception.UserNotFoundException;
import com.rabo.bank.user.model.User;
import com.rabo.bank.user.model.UserDetailsImpl;
import com.rabo.bank.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

	}
	public User findByUserId(int userId) {
		return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
	}
	public Iterable<User> findAll() {
		Iterable<User> users = userRepository.findAll();
		if(users == null) {
			throw new UserNotFoundException("User details not found");
		}
		return users;
	}
	public User save(User user) {
		user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

}
