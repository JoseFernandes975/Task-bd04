package com.devsuperior.bds04.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e,HttpServletRequest request){
		ValidationError error = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Error in Validation");
		error.setMessage(e.getMessage());
		
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			error.addErros(f.getField(), f.getDefaultMessage());
		}
	
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
}
