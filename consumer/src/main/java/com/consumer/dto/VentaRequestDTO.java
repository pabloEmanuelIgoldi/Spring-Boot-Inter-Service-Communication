package com.consumer.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class VentaRequestDTO {
	
	@Schema(description = "Monto total de la venta.")
	@Positive(message = "El monto debe ser mayor que cero.")
	private double montoTotal;
	@Schema(description = "Lista de IDs de producto que componen la venta.")
	@NotEmpty(message = "El nombre no puede estar vacio.")
	private List<Long> idsProducto;
}
