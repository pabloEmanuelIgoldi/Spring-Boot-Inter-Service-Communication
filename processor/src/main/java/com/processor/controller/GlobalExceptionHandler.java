package com.processor.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.processor.dto.ApiResponseDTO;
import com.processor.exception.UnauthorizedException;
import com.processor.util.CodeResponseUtil;
import com.processor.util.MensajeResponseUtil;
import com.processor.exception.EntityNotFoundException;

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
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponseDTO<String>> handleUnauthorizedException(UnauthorizedException e) {
		log.error("GlobalExceptionHandler.handleUnauthorizedException(): " + e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponseDTO<>(true, MensajeResponseUtil.UNAUTHORIZED,
						CodeResponseUtil.UNAUTHORIZED, LocalDateTime.now(), e.getMensaje()));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponseDTO<String>> handleEntityNotFoundException(EntityNotFoundException e) {
		log.error("GlobalExceptionHandler.handleEntityNotFoundException(): " + e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponseDTO<>(true, MensajeResponseUtil.ENTITY_NOT_FOUND,
						CodeResponseUtil.ENTITY_NOT_FOUND, LocalDateTime.now(), e.getMensaje()));
	}

}
