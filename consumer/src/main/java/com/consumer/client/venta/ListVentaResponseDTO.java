package com.consumer.client.venta;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ListVentaResponseDTO {
	
	private boolean success;
	private String message;
	private int code;
	private LocalDateTime timestamp;
	private List<VentaDTO> data;
}
