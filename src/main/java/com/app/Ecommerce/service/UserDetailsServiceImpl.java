package com.app.Ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.Ecommerce.model.User;
import com.app.Ecommerce.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user = userRepository.findByEmailId(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        return org.springframework.security.core.userdetails.User
	                .withUsername(user.getEmailId())
	                .password(user.getPassword())
	                .authorities(Collections.emptyList())
	                .build();
	}
	
	

}
