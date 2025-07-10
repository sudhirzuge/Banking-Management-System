package com.bank.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserLockedException.class)
	public ResponseEntity<Map<String, String>> handleUserLockedException(UserLockedException ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.LOCKED).body(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(InSufficientBalanceException.class)
	public ResponseEntity<Map<String, String>> handleInSufficientBalanceException(InSufficientBalanceException ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", ex.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(UnAuthorizedAccessException.class)
	public ResponseEntity<Map<String, String>> handleUnAuthorizedAccessException(UnAuthorizedAccessException ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	}
	
	@ExceptionHandler(InvalidIfscException.class)
	public ResponseEntity<Map<String, String>> handleInvalidIfscException(InvalidIfscException ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", "An unexpected error occured"+ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGenericException(Exception ex){
		Map<String, String> response = new HashMap<>();
		response.put("error", "An unexpected error occured"+ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	
}
