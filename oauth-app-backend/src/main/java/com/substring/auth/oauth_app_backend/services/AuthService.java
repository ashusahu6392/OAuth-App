package com.substring.auth.oauth_app_backend.services;

import com.substring.auth.oauth_app_backend.dtos.UserDto;

public interface AuthService {
	UserDto registerUser(UserDto userDto);
}
