package com.fseiji.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fseiji.cursomc.services.exceptions.AuthorizationException;
import com.fseiji.cursomc.services.exceptions.DataIntegrityException;
import com.fseiji.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> methodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				System.currentTimeMillis());

		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			validationError.addErrors(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
	}

}
