package com.consumer.client.producto;

import lombok.Data;

@Data
public class ProductoDTO {
	private Long id;
	private String nombre;
	private Double precio;
}