package com.substring.auth.oauth_app_backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.substring.auth.oauth_app_backend.entities.User;
import com.substring.auth.oauth_app_backend.exception.ResourceNotFoundException;
import com.substring.auth.oauth_app_backend.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;


	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() ->
	                    new UsernameNotFoundException("Invalid Email or Password"));

	    return user;   // Your entity ALREADY implements UserDetails ✅
	}


}
