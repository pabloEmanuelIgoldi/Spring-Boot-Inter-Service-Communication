package com.consumer.client.venta;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class VentaDTO {
	
	private Long id;
	private Date fecha;	
	private double montoTotal;
	private List<Long> idsProducto;
}
