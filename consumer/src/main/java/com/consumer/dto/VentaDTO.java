package com.consumer.dto;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VentaDTO {
	
	@Schema(description = "ID que identifica la venta.")
	private Long id;
	@Schema(description = "Fecha en que se realizo la venta.")
	private Date fecha;
	@Schema(description = "Monto total de la venta.")
	private double montoTotal;
	@Schema(description = "Lista de IDs de producto que componen la venta.")
	private List<Long> idsProducto;	
}
