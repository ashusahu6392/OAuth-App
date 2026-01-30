package com.substring.auth.oauth_app_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.substring.auth.oauth_app_backend.dtos.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	                  
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourseNotFoundException(ResourceNotFoundException exception){
		ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
	}

}
