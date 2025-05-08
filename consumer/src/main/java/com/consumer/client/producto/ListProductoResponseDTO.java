package com.consumer.client.producto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ListProductoResponseDTO {
	
	private boolean success;
	private String message;
	private int code;
	private LocalDateTime timestamp;
	private List<ProductoDTO> data;
}
