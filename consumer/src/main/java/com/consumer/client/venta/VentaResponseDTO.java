package com.consumer.client.venta;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VentaResponseDTO {
	
	private boolean success;
	private String message;
	private int code;
	private LocalDateTime timestamp;
	private VentaDTO data;
}
