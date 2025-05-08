package com.consumer.client.venta;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentaRequestDTO {
	private double montoTotal;
	private List<Long> idsProducto;
}
