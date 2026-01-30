package com.substring.auth.oauth_app_backend.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException() {
		super("Resource Not Found!!!");
	}

}
