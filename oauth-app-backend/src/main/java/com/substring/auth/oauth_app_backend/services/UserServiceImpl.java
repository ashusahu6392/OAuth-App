package com.substring.auth.oauth_app_backend.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.substring.auth.oauth_app_backend.dtos.UserDto;
import com.substring.auth.oauth_app_backend.entities.User;
import com.substring.auth.oauth_app_backend.exception.ResourceNotFoundException;
import com.substring.auth.oauth_app_backend.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	@Override
	@Transactional
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not Found with given ID") );
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map((User user) -> modelMapper.map(user, UserDto.class)).toList();
	}

}
