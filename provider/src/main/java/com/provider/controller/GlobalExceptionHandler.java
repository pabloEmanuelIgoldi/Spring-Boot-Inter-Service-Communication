package com.provider.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.provider.dto.ApiResponseDTO;
import com.provider.exception.EntityNotFoundException;
import com.provider.exception.UnauthorizedException;
import com.provider.util.CodeResponseUtil;
import com.provider.util.MensajeResponseUtil;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDTO<String>> handleException(Exception e) {
		log.error("GlobalExceptionHandler.handleException(): " + e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>(true,
				MensajeResponseUtil.INTERNAL_ERROR, CodeResponseUtil.INTERNAL_ERROR, LocalDateTime.now(), null));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleConstraintViolationException(
			ConstraintViolationException ex) {
		log.error("GlobalExceptionHandler.handleConstraintViolationException(): " + ex);
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(violation -> {
			String field = extractFieldName(violation.getPropertyPath().toString());
			errors.put(field, violation.getMessage());
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO<Map<String, String>>(true,
				MensajeResponseUtil.BAD_REQUEST, CodeResponseUtil.BAD_REQUEST, LocalDateTime.now(), errors));
	}

	private String extractFieldName(String propertyPath) {
		// Convierte "getProduct.id" → "id"
		return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponseDTO<String>> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error("GlobalExceptionHandler.handleEntityNotFoundException(): " + e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponseDTO<>(true, MensajeResponseUtil.ENTITY_NOT_FOUND,
						CodeResponseUtil.ENTITY_NOT_FOUND, LocalDateTime.now(), e.getMensaje()));
	}
	
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponseDTO<String>> handleUnauthorizedException(UnauthorizedException e) {
		log.error("GlobalExceptionHandler.handleUnauthorizedException(): " + e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDTO<>(true, MensajeResponseUtil.UNAUTHORIZED,
						CodeResponseUtil.UNAUTHORIZED, LocalDateTime.now(), e.getMensaje()));
	}
}