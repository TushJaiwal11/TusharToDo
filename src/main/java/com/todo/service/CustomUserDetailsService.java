package com.todo.service;

import java.util.ArrayList;
import java.util.List;


import com.todo.model.User;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User isExist = userRepository.findUserByEmail(username);
		
		if(isExist==null) {
			throw new BadCredentialsException("User not found with email id :"+username);
		}
		List<GrantedAuthority> authorities=new ArrayList<>();
		
		
		return new org.springframework.security.core.userdetails
				.User(isExist.getEmail(), isExist.getPassword(), authorities);
	}

}
