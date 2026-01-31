package com.substring.auth.oauth_app_backend.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.substring.auth.oauth_app_backend.dtos.UserDto;
import com.substring.auth.oauth_app_backend.entities.Provider;
import com.substring.auth.oauth_app_backend.entities.User;
import com.substring.auth.oauth_app_backend.exception.ResourceNotFoundException;
import com.substring.auth.oauth_app_backend.helpers.UserHelper;
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
		 if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
	            throw new IllegalArgumentException("Email is required");
	        }
	        if (userRepository.existsByEmail(userDto.getEmail())) {
	            throw new IllegalArgumentException("User with given email already exists");
	        }
	        // if you have extra checks __put here...
	        User user = modelMapper.map(userDto, User.class);
	        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);
	        User savedUser = userRepository.save(user);
	        return modelMapper.map(savedUser, UserDto.class);
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
		UUID uId = UserHelper.parseUUID(userId);
		User existingUser = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User not found with with given Id"));
		
		if(userDto.getName() != null) existingUser.setName(userDto.getName());
		if(userDto.getImage() != null) existingUser.setImage(userDto.getImage());
		if(userDto.getProvider() !=null) existingUser.setProvider(userDto.getProvider());
		
		if(userDto.getPassword() !=null) existingUser.setPassword(userDto.getPassword());
		existingUser.setEnable(userDto.isEnable());
		
		User updatedUser = userRepository.save(existingUser);
		return modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		UUID uId = UserHelper.parseUUID(userId);
		User user = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given ID"));
		userRepository.delete(user);
	}

	@Override
	public UserDto getUserById(String userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Given ID"));
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	@Transactional
	public Iterable<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll().stream().map((User user) -> modelMapper.map(user, UserDto.class)).toList();
	}

}
