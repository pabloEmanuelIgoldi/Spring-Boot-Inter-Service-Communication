package com.processor.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class VentaRequestDTO {
	private double montoTotal;
	private List<Long> idsProducto;
}
