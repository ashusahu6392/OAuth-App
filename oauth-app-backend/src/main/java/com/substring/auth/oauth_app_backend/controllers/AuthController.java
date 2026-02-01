package com.substring.auth.oauth_app_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.substring.auth.oauth_app_backend.dtos.UserDto;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(UserDto userDto){
		return ResponseEntity.ok(userDto);
	}

}
