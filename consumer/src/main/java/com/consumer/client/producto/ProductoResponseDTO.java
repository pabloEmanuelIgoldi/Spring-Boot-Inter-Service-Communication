package com.consumer.client.producto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductoResponseDTO {
	
	private boolean success;
	private String message;
	private int code;
	private LocalDateTime timestamp;
	private ProductoDTO data;
}
